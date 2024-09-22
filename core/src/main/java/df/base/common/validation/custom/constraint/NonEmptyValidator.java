package df.base.common.validation.custom.constraint;

import df.base.common.validation.custom.ValidationContext;
import df.base.common.validation.custom.ValidationException;
import df.base.common.validation.custom.Validator;

import static df.base.common.validation.custom.ErrorCode.Default.STRING_BLANK;

public class NonEmptyValidator implements Validator {

    private String message;

    @Override
    public void validate(Object object, ValidationContext validationContext) throws ValidationException {
        if (object == null || (object instanceof String string && string.isBlank())) {
            throw new ValidationException(STRING_BLANK, message);
        }
    }

}
