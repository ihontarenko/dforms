package df.base.common.pipeline;

import df.base.common.exception.ApplicationException;

public class PipelineRuntimeException extends RuntimeException  {

    public PipelineRuntimeException() {
        super();
    }

    public PipelineRuntimeException(String message) {
        super(message);
    }

    public PipelineRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipelineRuntimeException(Throwable cause) {
        super(cause);
    }

}
