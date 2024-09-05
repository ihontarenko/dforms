package df.base.common.exception;

public class MethodNotImplemented extends ApplicationException {

    public MethodNotImplemented(String message) {
        super(message);
    }

    public MethodNotImplemented(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotImplemented(Throwable cause) {
        super(cause);
    }

}
