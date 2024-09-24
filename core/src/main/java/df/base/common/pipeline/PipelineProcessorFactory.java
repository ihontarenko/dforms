package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.definition.RootDefinition;
import df.base.common.pipeline.definition.RootDefinition.Processor;
import df.base.common.resolver.Resolver;
import df.base.common.resolver.Resolvers;
import df.base.common.resolver.ResolversFactory;

import static df.base.common.libs.jbm.ReflectionUtils.instantiate;
import static df.base.common.libs.jbm.ReflectionUtils.setFieldValue;
import static df.base.common.resolver.Resolvers.valueOf;

public class PipelineProcessorFactory {

    private final ResolversFactory resolversFactory;

    public PipelineProcessorFactory() {
        this.resolversFactory = new ResolversFactory();
    }

    public PipelineProcessor createProcessor(Processor processorDefinition) {
        try {

            Class<?>          processorClass = Class.forName(processorDefinition.className());
            PipelineProcessor processor      = (PipelineProcessor) instantiate(processorClass.getDeclaredConstructor());

            if (processorDefinition.parameters() != null) {
                for (RootDefinition.Parameter parameter : processorDefinition.parameters()) {
                    Object resolvedValue = resolveParameter(parameter);
                    setFieldValue(processor, parameter.name(), resolvedValue);
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
