package df.base.common.pipeline;

public interface PipelineProcessor {
    Enum<?> process(PipelineContext context) throws Exception;
}
