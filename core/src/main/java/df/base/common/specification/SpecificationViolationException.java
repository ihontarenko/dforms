package df.base.common.specification;

public class SpecificationViolationException extends RuntimeException {

    public SpecificationViolationException(String message) {
        super(message);
    }

    public SpecificationViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpecificationViolationException(Throwable cause) {
        super(cause);
    }
}
