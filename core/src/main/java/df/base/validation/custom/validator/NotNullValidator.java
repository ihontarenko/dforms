package df.base.validation.custom.validator;

import df.base.internal.validation.*;

import static df.base.internal.validation.ErrorCode.Default.NULL_OBJECT;

public class NotNullValidator implements Validator {

    @Override
    public void validate(Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(NULL_OBJECT);
        }
    }

}
