package df.base.common.validation.custom;

public interface Validator {

    void validate(Object object);

    default boolean supports(Class<?> objectType) {
        return true;
    }

}
