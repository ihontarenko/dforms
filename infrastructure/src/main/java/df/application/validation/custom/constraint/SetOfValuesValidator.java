package df.application.validation.custom.constraint;

import org.jmouse.validator.old.AbstractValidator;
import org.jmouse.validator.old.Errors;
import org.jmouse.validator.old.ValidationContext;
import org.jmouse.validator.old.ValidationException;

public class SetOfValuesValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) throws ValidationException {

    }

    @Override
    public boolean supports(Object object) {
        return true;
    }

}
