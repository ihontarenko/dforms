package df.base.common.operation;

public class OperationManagerException extends RuntimeException {

    public OperationManagerException() {
    }

    public OperationManagerException(String message) {
        super(message);
    }

    public OperationManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationManagerException(Throwable cause) {
        super(cause);
    }

}
