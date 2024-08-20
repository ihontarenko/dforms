package df.base.common.validation;

import java.util.List;

public final class ValidatorWrapper extends AbstractValidator<Object> {

    public ValidatorWrapper(List<Validator<Object>> validators) {
        validators.forEach(this::addChild);
    }

    @Override
    public void validate(Object value, ValidationResult result) {
        children.forEach(validator -> validator.validate(value, result));
    }

}
