package df.common.container;

public class BeanReflectionException extends RuntimeException {

    public BeanReflectionException() {
        super();
    }

    public BeanReflectionException(String message) {
        super(message);
    }

    public BeanReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanReflectionException(Throwable cause) {
        super(cause);
    }

}
