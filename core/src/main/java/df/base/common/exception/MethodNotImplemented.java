package df.base.common.exception;

public class MethodNotImplemented extends ApplicationException {

    public MethodNotImplemented(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public MethodNotImplemented(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public MethodNotImplemented(String message) {
        super(message);
    }

    public MethodNotImplemented(Throwable cause) {
        super(cause);
    }

    public MethodNotImplemented(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
