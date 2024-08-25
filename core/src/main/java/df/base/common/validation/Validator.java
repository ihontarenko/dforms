package df.base.common.validation;

public interface Validator {

    String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "'%s' UNSUPPORTED CALL";

    default void validate(Object object) throws ValidationException {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE
                .formatted(this.getClass().getSimpleName()));
    }

    default void validate(Object object, Errors errors) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE
                .formatted(this.getClass().getSimpleName()));
    }

    boolean supports(Class<?> objectType);

}
