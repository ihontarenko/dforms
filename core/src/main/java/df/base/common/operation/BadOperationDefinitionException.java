package df.base.common.operation;

public class BadOperationDefinitionException extends RuntimeException {

    public BadOperationDefinitionException() {
    }

    public BadOperationDefinitionException(String message) {
        super(message);
    }

    public BadOperationDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadOperationDefinitionException(Throwable cause) {
        super(cause);
    }

}
