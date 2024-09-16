package df.base.common.pipeline.definition;

import df.base.common.exception.ApplicationException;

public class PipelineDefinitionException extends ApplicationException  {

    public PipelineDefinitionException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public PipelineDefinitionException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public PipelineDefinitionException(String message) {
        super(message);
    }

    public PipelineDefinitionException(Throwable cause) {
        super(cause);
    }

    public PipelineDefinitionException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
