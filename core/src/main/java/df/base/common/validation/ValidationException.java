package df.base.common.validation;

public class ValidationException extends RuntimeException {

    private final String errorCode;
    private final String errorContext;

    public ValidationException(String errorCode, String errorContext, Throwable cause) {
        super(cause);
        this.errorContext = errorContext;
        this.errorCode = errorCode;
    }

    public ValidationException(String errorCode) {
        this(errorCode, null);
    }

    public ValidationException(String errorCode, String errorContext) {
        this(errorCode, errorContext, null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorContext() {
        return errorContext;
    }
}

