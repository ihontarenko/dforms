package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineContext;

public interface PipelineChain {
    void proceed(PipelineContext context) throws Exception;
}
