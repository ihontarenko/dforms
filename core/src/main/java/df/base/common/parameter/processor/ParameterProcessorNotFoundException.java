package df.base.common.parameter.processor;

import df.base.common.exception.ApplicationException;

public class ParameterProcessorNotFoundException extends ApplicationException {
    public ParameterProcessorNotFoundException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public ParameterProcessorNotFoundException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public ParameterProcessorNotFoundException(String message) {
        super(message);
    }

    public ParameterProcessorNotFoundException(Throwable cause) {
        super(cause);
    }

    public ParameterProcessorNotFoundException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }
}
