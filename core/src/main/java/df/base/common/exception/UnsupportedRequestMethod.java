package df.base.common.exception;

public class UnsupportedRequestMethod extends ApplicationException {

    public UnsupportedRequestMethod(String message) {
        super(message);
    }

    public UnsupportedRequestMethod(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedRequestMethod(Throwable cause) {
        super(cause);
    }

}
