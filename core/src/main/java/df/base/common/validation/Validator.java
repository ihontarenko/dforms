package df.base.common.validation;

public interface Validator<T> {

    String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "'%s' UNSUPPORTED CALL";

    default void initialize(Configuration configuration) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE
                .formatted(this.getClass().getSimpleName()));
    }

    default void validate(T value) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE
                .formatted(this.getClass().getSimpleName()));
    }

    default void addChild(Validator<T> child) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE
                .formatted(this.getClass().getSimpleName()));
    }



}
