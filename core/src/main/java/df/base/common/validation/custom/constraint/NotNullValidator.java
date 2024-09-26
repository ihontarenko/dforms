package df.base.common.validation.custom.constraint;

import df.base.common.validation.custom.AbstractValidator;
import df.base.common.validation.custom.Errors;
import df.base.common.validation.custom.ValidationContext;

public class NotNullValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) {
        if (object == null) {
            errors.rejectValue(context.getPointer(), getMessage());
        }
    }

}
