package df.base.common.validation.validator;

import df.base.common.validation.AbstractValidator;
import df.base.common.validation.ValidationException;

import static df.base.common.validation.StandardErrorCode.NOT_NULL;
import static df.base.common.validation.StandardErrorContext.USER_FORM;

public class NotNullValidator extends AbstractValidator<String> {

    @Override
    public void validate(String value) {
        if (value == null) {
            throw new ValidationException(NOT_NULL, USER_FORM);
        }
    }

}

