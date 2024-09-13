package df.base.common.parameter;

import df.base.common.exception.ApplicationException;

public class ParameterValueNotFoundException extends ApplicationException {
    public ParameterValueNotFoundException(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public ParameterValueNotFoundException(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public ParameterValueNotFoundException(String message) {
        super(message);
    }

    public ParameterValueNotFoundException(Throwable cause) {
        super(cause);
    }

    public ParameterValueNotFoundException(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }
}
