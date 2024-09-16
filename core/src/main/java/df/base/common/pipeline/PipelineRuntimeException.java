package df.base.common.pipeline;

import df.base.common.exception.ApplicationException;

public class PipelineRuntimeException extends ApplicationException  {

    public PipelineRuntimeException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public PipelineRuntimeException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public PipelineRuntimeException(String message) {
        super(message);
    }

    public PipelineRuntimeException(Throwable cause) {
        super(cause);
    }

    public PipelineRuntimeException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
