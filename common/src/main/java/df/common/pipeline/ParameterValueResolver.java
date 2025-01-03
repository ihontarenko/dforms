package df.common.pipeline;

import df.common.pipeline.context.PipelineContext;

public interface ParameterValueResolver {
    Object resolve(String name, String value, PipelineContext ctx);
}
