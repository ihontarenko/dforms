package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.definition.PipelineDefinitionException;
import df.base.common.pipeline.definition.RootDefinition;
import df.base.common.pipeline.definition.RootDefinition.Fallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static df.base.common.pipeline.definition.DefinitionLoader.createLoader;

public class PipelineManager {

    private final Map<String, PipelineChain> chains = new HashMap<>();
    private final RootDefinition           rootDefinition;
    private final PipelineContext          context;
    private final PipelineProcessorFactory processorFactory;

    public PipelineManager(PipelineContext context, String definition) {
        this.rootDefinition = createLoader(definition).load(definition);
        this.context = context;
        this.processorFactory = new PipelineProcessorFactory();
    }

    public RootDefinition getRootDefinition() {
        return rootDefinition;
    }

    public PipelineContext getContext() {
        return context;
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
            Map<String, String>                transitions          = propertiesDefinition == null ? new HashMap<>() : propertiesDefinition.transitions();
            Map<String, String>                configuration        = propertiesDefinition == null ? new HashMap<>() : propertiesDefinition.configuration();
            Optional<Fallback>                 fallback             = propertiesDefinition == null ? Optional.empty() : Optional.ofNullable(
                    propertiesDefinition.fallback());

            processors.put(linkName, processor);
            properties.put(linkName, new ProcessorProperties(transitions, configuration, fallback.map(Fallback::link).orElse(null)));
        });

        PipelineChain chain = new PipelineProcessorChain(chainDefinition.initial(), processors, properties);

        chains.put(chainDefinition.name(), chain);

        return chain;
    }

    public void runPipeline(String chainName) throws Exception {
        this.context.getResultContext().cleanup();
        createProcessorChain(chainName).proceed(this.context);
    }

}
