package df.base.common.validation.custom;

public interface Validator {

    void validate(Object object, Errors errors, ValidationContext context) throws ValidationException;

    default boolean supports(Object object) {
        return true;
    }

}
