package df.application.validation.custom.constraint;

import df.common.validation.custom.AbstractValidator;
import df.common.validation.custom.Errors;
import df.common.validation.custom.ValidationContext;
import df.common.validation.custom.ValidationException;

public class SetOfValuesValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) throws ValidationException {

    }

    @Override
    public boolean supports(Object object) {
        return true;
    }

}
