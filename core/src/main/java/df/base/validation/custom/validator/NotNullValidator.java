package df.base.validation.custom.validator;

import df.base.common.validation.*;

import static df.base.common.validation.ErrorCode.Default.NULL_OBJECT;

public class NotNullValidator implements Validator {

    @Override
    public void validate(Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(NULL_OBJECT);
        }
    }

    @Override
    public boolean supports(Class<?> objectType) {
        return true;
    }

}
