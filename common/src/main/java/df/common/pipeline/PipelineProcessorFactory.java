package df.common.pipeline;

import df.common.pipeline.definition.RootDefinition;
import df.common.pipeline.definition.RootDefinition.Processor;
import df.common.reflection.Reflections;
import df.common.resolver.Resolver;
import df.common.resolver.Resolvers;
import df.common.resolver.ResolversFactory;

import static df.common.resolver.Resolvers.valueOf;

public class PipelineProcessorFactory {

    private final ResolversFactory resolversFactory;

    public PipelineProcessorFactory() {
        this.resolversFactory = new ResolversFactory();
    }

    public PipelineProcessor createProcessor(Processor processorDefinition) {
        try {

            Class<?>          processorClass = Class.forName(processorDefinition.className());
            PipelineProcessor processor      = (PipelineProcessor) Reflections.instantiate(processorClass.getDeclaredConstructor());

            if (processorDefinition.parameters() != null) {
                for (RootDefinition.Parameter parameter : processorDefinition.parameters()) {
                    Object resolvedValue = resolveParameter(parameter);
                    Reflections.setFieldValue(processor, parameter.name(), resolvedValue);
                }
            }

            return processor;
        } catch (Exception e) {
            throw new ProcessorInstantiationException(
                    "Error creating processor: '%s'".formatted(processorDefinition.className()), e);
        }
    }

    private Object resolveParameter(RootDefinition.Parameter parameter) {
        Resolvers resolverType = valueOf(parameter.resolver().toUpperCase());
        Resolver  resolver     = resolversFactory.createResolver(resolverType);

        return resolver.resolve(parameter.value(), null);
    }

}