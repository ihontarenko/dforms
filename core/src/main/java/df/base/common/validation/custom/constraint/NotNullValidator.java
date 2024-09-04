package df.base.common.validation.custom.constraint;

import df.base.common.validation.custom.ValidationException;
import df.base.common.validation.custom.Validator;

import static df.base.common.validation.custom.ErrorCode.Default.NULL_OBJECT;

public class NotNullValidator implements Validator {

    @Override
    public void validate(Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(NULL_OBJECT);
        }
    }

}