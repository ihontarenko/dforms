package df.base.common.context.beans;

public class BeanFieldNotFoundException extends RuntimeException {

    public BeanFieldNotFoundException() {
        super();
    }

    public BeanFieldNotFoundException(String message) {
        super(message);
    }

    public BeanFieldNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanFieldNotFoundException(Throwable cause) {
        super(cause);
    }

}
