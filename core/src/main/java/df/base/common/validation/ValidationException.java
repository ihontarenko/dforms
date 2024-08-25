package df.base.common.validation;

import df.base.common.validation.ErrorCode;
import df.base.common.validation.ErrorContext;

public class ValidationException extends RuntimeException {

    private final ErrorCode    errorCode;
    private final ErrorContext errorContext;

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext, Throwable cause) {
        super(cause);
        this.errorContext = errorContext;
        this.errorCode = errorCode;
    }

    public ValidationException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext) {
        this(errorCode, errorContext, null);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }
}

