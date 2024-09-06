package df.base.common.validation.custom;

public class ValidationException extends RuntimeException {

    private final ErrorCode    errorCode;
    private final ErrorContext errorContext;

    private final boolean breakOnFail;

    public ValidationException(String message, Throwable cause, ErrorCode errorCode, ErrorContext errorContext, boolean breakOnFail) {
        super(message, cause);
        this.errorContext = errorContext;
        this.errorCode = errorCode;
        this.breakOnFail = breakOnFail;
    }

    public ValidationException(ErrorCode errorCode, boolean breakOnFail) {
        this(null, null, errorCode, null, breakOnFail);
    }

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext, boolean breakOnFail) {
        this(null, null, errorCode, errorContext, breakOnFail);
    }

    public ValidationException(ErrorCode errorCode) {
        this(null, null, errorCode, null, false);
    }

    public ValidationException(ErrorCode errorCode, String message) {
        this(message, null, errorCode, null, false);
    }

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext) {
        this(null, null, errorCode, errorContext, false);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public boolean isBreakOnFail() {
        return breakOnFail;
    }
}

