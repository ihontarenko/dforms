package df.base.common.commans;

public class RouteDuplicationException extends RuntimeException {

    public RouteDuplicationException() {
    }

    public RouteDuplicationException(String message) {
        super(message);
    }

    public RouteDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteDuplicationException(Throwable cause) {
        super(cause);
    }

}
