package df.base.common.validation.custom.constraint;

import df.base.common.validation.custom.ValidationException;
import df.base.common.validation.custom.Validator;

import static df.base.common.validation.custom.ErrorCode.Default.STRING_BLANK;

public class NotBlankValidator implements Validator {

    @Override
    public void validate(Object object) throws ValidationException {
        if (object == null || !CharSequence.class.isAssignableFrom(object.getClass()) || ((CharSequence) object).isEmpty()) {
            throw new ValidationException(STRING_BLANK);
        }
    }

}
