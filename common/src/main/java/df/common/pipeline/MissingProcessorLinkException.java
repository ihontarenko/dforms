package df.common.pipeline;

public class MissingProcessorLinkException extends PipelineRuntimeException {
    public MissingProcessorLinkException(String message) {
        super(message);
    }
}
