package df.base.common.validation.custom.constraint;

import df.base.common.validation.custom.*;

public class NonEmptyValidator extends AbstractValidator {

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) {
        if (object == null || (object instanceof String string && string.isBlank())) {
            errors.rejectValue(context.getPointer(), getMessage());
        }
    }

}
