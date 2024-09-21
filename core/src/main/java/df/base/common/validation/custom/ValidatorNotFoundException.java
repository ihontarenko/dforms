package df.base.common.validation.custom;

public class ValidatorNotFoundException extends RuntimeException {

    public ValidatorNotFoundException() {
        super();
    }

    public ValidatorNotFoundException(String message) {
        super(message);
    }

    public ValidatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorNotFoundException(Throwable cause) {
        super(cause);
    }

}
