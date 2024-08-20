package df.base.common.validation.validator;

import df.base.common.validation.AbstractValidator;
import df.base.common.validation.ValidationResult;

public class NotNullValidator extends AbstractValidator<String> {

    @Override
    public void validate(String value, ValidationResult result) {
        if (value == null) {

        }
    }
}

