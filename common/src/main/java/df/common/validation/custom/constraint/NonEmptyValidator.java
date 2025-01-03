package df.common.validation.custom.constraint;

import df.common.validation.custom.AbstractValidator;
import df.common.validation.custom.Errors;
import df.common.validation.custom.ValidationContext;

public class NonEmptyValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) {
        if (object == null || (object instanceof String string && string.isBlank())) {
            errors.rejectValue(context.getPointer(), getMessage());
        }
    }

}
