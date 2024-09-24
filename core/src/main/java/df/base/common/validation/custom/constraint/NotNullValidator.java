package df.base.common.validation.custom.constraint;

import df.base.common.validation.custom.Errors;
import df.base.common.validation.custom.ValidationContext;
import df.base.common.validation.custom.Validator;

import static df.base.common.validation.custom.ErrorCode.Default.NULL_OBJECT;

public class NotNullValidator implements Validator {

    private String message;

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) {
        if (object == null) {
            errors.rejectValue(context.getPointer(), message);
        }
    }

}
