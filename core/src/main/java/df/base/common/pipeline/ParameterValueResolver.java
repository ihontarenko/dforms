package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineContext;

public interface ParameterValueResolver {
    Object resolve(String name, String value, PipelineContext ctx);
}
