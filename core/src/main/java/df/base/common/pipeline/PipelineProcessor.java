package df.base.common.pipeline;

public interface PipelineProcessor {
    void process(PipelineContext context, PipelineChain chain) throws Exception;
}
