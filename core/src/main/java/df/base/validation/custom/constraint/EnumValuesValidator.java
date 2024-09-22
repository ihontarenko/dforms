package df.base.validation.custom.constraint;

import df.base.common.validation.custom.ValidationContext;
import df.base.common.validation.custom.ValidationException;
import df.base.common.validation.custom.Validator;

public class EnumValuesValidator implements Validator {

    @Override
    public void validate(Object object, ValidationContext validationContext) throws ValidationException {

    }

    @Override
    public boolean supports(Class<?> objectType) {
        return true;
    }

}
