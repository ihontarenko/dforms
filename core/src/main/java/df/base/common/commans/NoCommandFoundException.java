package df.base.common.commans;

public class NoCommandFoundException extends RuntimeException {

    public NoCommandFoundException() {
    }

    public NoCommandFoundException(String message) {
        super(message);
    }

    public NoCommandFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCommandFoundException(Throwable cause) {
        super(cause);
    }

}
