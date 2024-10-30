package df.base.common.pipeline;

import df.base.PackageCoreRoot;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.definition.PipelineDefinitionException;
import df.base.common.pipeline.definition.RootDefinition;
import df.base.common.pipeline.definition.RootDefinition.Fallback;
import df.base.common.proxy.AnnotationProxyFactory;
import df.base.common.proxy.ProxyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static df.base.common.pipeline.definition.DefinitionLoader.createLoader;

public class PipelineManager {

    private final Map<String, PipelineChain> chains = new HashMap<>();
    private final RootDefinition             rootDefinition;
    private final PipelineProcessorFactory   processorFactory;

    public PipelineManager(String definition) {
        this.rootDefinition = createLoader(definition).load(definition);
        this.processorFactory = new PipelineProcessorFactory();
    }

    public RootDefinition getRootDefinition() {
        return rootDefinition;
    }

    public PipelineChain createProcessorChain(String chainName) {
        RootDefinition.Chain chainDefinition = rootDefinition.chains().get(chainName);

        if (chainDefinition == null) {
            throw new PipelineDefinitionException("No chain definition found: " + chainName);
        }

        if (chains.containsKey(chainDefinition.name())) {
            return chains.get(chainDefinition.name());
        }

        Map<String, PipelineProcessor>   processors = new HashMap<>();
        Map<String, ProcessorProperties> properties = new HashMap<>();

        chainDefinition.links().forEach((linkName, linkDefinition) -> {
            RootDefinition.ProcessorProperties propertiesDefinition = linkDefinition.properties();
            PipelineProcessor                  processor            = processorFactory.createProcessor(
                    linkDefinition.processor());
            Map<String, String>                transitions          = propertiesDefinition == null ? new HashMap<>()
                    : propertiesDefinition.transitions();
            Map<String, String>                configuration        = propertiesDefinition == null ? new HashMap<>()
                    : propertiesDefinition.configuration();
            Optional<Fallback> fallback = propertiesDefinition == null ? Optional.empty() : Optional.ofNullable(
                    propertiesDefinition.fallback());

            ProxyFactory proxyFactory = new AnnotationProxyFactory(processor, PackageCoreRoot.class);

            processors.put(linkName, proxyFactory.getProxy());
            properties.put(linkName, new ProcessorProperties(
                    transitions, configuration, fallback.map(Fallback::link).orElse(null)));
        });

        PipelineChain chain = new PipelineProcessorChain(chainDefinition.initial(), processors, properties);
        PipelineChain proxy = new AnnotationProxyFactory(chain, PackageCoreRoot.class).getProxy();

        chains.put(chainDefinition.name(), proxy);

        return proxy;
    }

    public void runPipeline(String chainName, PipelineContext context) throws Exception {
        context.getResultContext().cleanup();
        createProcessorChain(chainName).proceed(context);
    }

}
