package df.common.validation.custom.constraint;

import df.common.validation.custom.AbstractValidator;
import df.common.validation.custom.Errors;
import df.common.validation.custom.ValidationContext;

public class NumberRangeValidator extends AbstractValidator {

    private Number min;
    private Number max;

    @Override
    public void validate(Object object, Errors errors, ValidationContext context) {
        if (object instanceof Number number && (number.doubleValue() < min.intValue() || number.doubleValue() > max.intValue())) {
            errors.rejectValue(context.getPointer(), getMessage());
        }
    }

    @Override
    public String toString() {
        return "%s : [min=%d, max='%d']".formatted(super.toString(), min.intValue(), max.intValue());
    }

}