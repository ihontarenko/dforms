package df.base.common.pipeline;

import df.base.common.pipeline.definition.PipelineDefinitionException;
import df.base.common.pipeline.definition.RootDefinition;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.pipeline.definition.DefinitionLoader.createLoader;

public class PipelineManager {

    private final Map<String, PipelineChain> chains = new HashMap<>();
    private final RootDefinition             rootDefinition;
    private final PipelineContext            context;
    private final PipelineProcessorFactory   processorFactory;

    public PipelineManager(PipelineContext context, String definition) {
        this.rootDefinition = createLoader(definition).load(definition);
        this.context = context;
        this.processorFactory = new PipelineProcessorFactory();
        this.context.setProperty(RootDefinition.class, rootDefinition);
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

        Map<String, PipelineProcessor> processors  = new HashMap<>();
        Map<String, String>            transitions = new HashMap<>();

        chainDefinition.links().forEach((linkName, linkDefinition) -> {
            PipelineProcessor processor = processorFactory.createProcessor(linkDefinition.processor());

            processors.put(linkName, processor);

            linkDefinition.transitions().forEach((returnCode, nextProcessor)
                -> transitions.put(returnCode, nextProcessor));
        });

        PipelineChain chain = new PipelineProcessorChain(processors, new Transitions(chainDefinition.initial(), transitions));

        chains.put(chainDefinition.name(), chain);

        return chain;
    }

    public void runPipeline(String chainName) throws Exception {
        createProcessorChain(chainName).proceed(this.context);
    }

}
