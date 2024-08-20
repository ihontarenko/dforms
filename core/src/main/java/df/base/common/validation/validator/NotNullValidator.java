package df.base.common.validation.validator;

import df.base.common.validation.AbstractValidator;
import df.base.common.validation.ValidationException;

public class NotNullValidator extends AbstractValidator<String> {

    @Override
    public void validate(String value) {
        if (value == null) {
            throw new ValidationException("NOT_NULL");
        }
    }

}

