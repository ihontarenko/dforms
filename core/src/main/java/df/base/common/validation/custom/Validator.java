package df.base.common.validation.custom;

public interface Validator {

    void validate(Object object, ValidationContext validationContext) throws ValidationException;

    default boolean supports(Class<?> objectType) {
        return true;
    }

}
