package df.base.internal.validation;

public interface Validator {

    void validate(Object object);

    default boolean supports(Class<?> objectType) {
        return true;
    }

}
