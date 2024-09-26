package df.base.validation.custom.constraint;

import df.base.common.validation.custom.*;

public class SetOfValuesValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) throws ValidationException {

    }

    @Override
    public boolean supports(Object object) {
        return true;
    }

}
