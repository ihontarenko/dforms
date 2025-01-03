package df.common.pipeline;

import df.common.pipeline.context.PipelineContext;

public interface PipelineChain {
    void proceed(PipelineContext context) throws Exception;
}
