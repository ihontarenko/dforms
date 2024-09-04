package df.base.common.validation.custom;

public class ValidationException extends RuntimeException {

    private final ErrorCode    errorCode;
    private final ErrorContext errorContext;

    private final boolean breakOnFail;

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext, Throwable cause, boolean breakOnFail) {
        super(cause);
        this.errorContext = errorContext;
        this.errorCode = errorCode;
        this.breakOnFail = breakOnFail;
    }

    public ValidationException(ErrorCode errorCode, boolean breakOnFail) {
        this(errorCode, null, breakOnFail);
    }

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext, boolean breakOnFail) {
        this(errorCode, errorContext, null, breakOnFail);
    }

    public ValidationException(ErrorCode errorCode) {
        this(errorCode, null, false);
    }

    public ValidationException(ErrorCode errorCode, ErrorContext errorContext) {
        this(errorCode, errorContext, null, false);
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

