package df.base.common.pipeline;

import df.base.common.exception.ApplicationException;

public class PipelineConfigLoadingException extends ApplicationException  {

    public PipelineConfigLoadingException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public PipelineConfigLoadingException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public PipelineConfigLoadingException(String message) {
        super(message);
    }

    public PipelineConfigLoadingException(Throwable cause) {
        super(cause);
    }

    public PipelineConfigLoadingException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
