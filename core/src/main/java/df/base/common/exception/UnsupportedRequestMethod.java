package df.base.common.exception;

public class UnsupportedRequestMethod extends ApplicationException {

    public UnsupportedRequestMethod(String message, String redirectUrl, Throwable cause) {
        super(message, redirectUrl, cause);
    }

    public UnsupportedRequestMethod(String message, String redirectUrl) {
        super(message, redirectUrl);
    }

    public UnsupportedRequestMethod(String message) {
        super(message);
    }

    public UnsupportedRequestMethod(Throwable cause) {
        super(cause);
    }

    public UnsupportedRequestMethod(Throwable cause, String redirectUrl) {
        super(cause, redirectUrl);
    }

}
