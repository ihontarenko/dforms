package df.base.validation.custom;

import df.base.common.validation.*;

public class ObjectNotNullValidator implements Validator {

    @Override
    public void validate(Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ErrorCode.Default.NULL_OBJECT);
        }
    }

    @Override
    public boolean supports(Class<?> objectType) {
        return true;
    }

}
