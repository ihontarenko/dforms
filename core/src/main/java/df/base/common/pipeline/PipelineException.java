package df.base.common.pipeline;

import df.base.common.exception.ApplicationException;

public class PipelineException extends ApplicationException  {

    public PipelineException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public PipelineException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public PipelineException(String message) {
        super(message);
    }

    public PipelineException(Throwable cause) {
        super(cause);
    }

    public PipelineException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
