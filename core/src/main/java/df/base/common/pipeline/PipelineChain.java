package df.base.common.pipeline;

public interface PipelineChain {
    void proceed(PipelineContext context) throws Exception;
}
