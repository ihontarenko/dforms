package df.base.common.pipeline;

import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.pipeline.definition.RootDefinition;

import static df.base.common.libs.jbm.ReflectionUtils.instantiate;
import static df.base.common.pipeline.Resolvers.valueOf;

public class PipelineProcessorFactory {

    private final ResolversFactory resolversFactory;

    public PipelineProcessorFactory() {
        this.resolversFactory = new ResolversFactory();
    }

    public PipelineProcessor createProcessor(RootDefinition.Processor processorDefinition) {
        try {

            Class<?>          processorClass = Class.forName(processorDefinition.className());
            PipelineProcessor processor      = (PipelineProcessor) instantiate(processorClass.getDeclaredConstructor());

            if (processorDefinition.parameters() != null) {
                for (RootDefinition.Parameter parameter : processorDefinition.parameters()) {
                    Object resolvedValue = resolveParameter(parameter);
                    ReflectionUtils.setFieldValue(processor, parameter.name(), resolvedValue);
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
        return resolver.resolve(parameter.value());
    }

}
