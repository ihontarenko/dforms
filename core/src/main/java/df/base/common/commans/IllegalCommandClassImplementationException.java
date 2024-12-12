package df.base.common.commans;

public class IllegalCommandClassImplementationException extends RuntimeException {

    public IllegalCommandClassImplementationException() {
    }

    public IllegalCommandClassImplementationException(String message) {
        super(message);
    }

    public IllegalCommandClassImplementationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCommandClassImplementationException(Throwable cause) {
        super(cause);
    }

}
