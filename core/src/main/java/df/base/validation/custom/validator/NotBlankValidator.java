package df.base.validation.custom.validator;

import df.base.common.validation.ValidationException;
import df.base.common.validation.Validator;

import static df.base.common.validation.ErrorCode.Default.STRING_BLANK;

public class NotBlankValidator implements Validator {

    @Override
    public void validate(Object object) throws ValidationException {
        if (object == null || !CharSequence.class.isAssignableFrom(object.getClass()) || ((CharSequence) object).isEmpty()) {
            throw new ValidationException(STRING_BLANK);
        }
    }

    @Override
    public boolean supports(Class<?> objectType) {
        return true;
    }

}
