package df.base.validation.custom.constraint;

import df.base.common.validation.custom.Errors;
import df.base.common.validation.custom.ValidationContext;
import df.base.common.validation.custom.ValidationException;
import df.base.common.validation.custom.Validator;

public class SetOfValuesValidator implements Validator {

    private String message;

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) throws ValidationException {

    }

    @Override
    public boolean supports(Object object) {
        return true;
    }

}
