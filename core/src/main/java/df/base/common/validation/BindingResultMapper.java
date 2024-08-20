package df.base.common.validation;

import df.base.mapper.Mapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public final class BindingResultMapper implements Mapper<ValidationResult, BindingResult> {

    public static void convertToBindingResult(ValidationResult validationResult, BindingResult bindingResult) {

    }

    @Override
    public void map(ValidationResult source, BindingResult destination) {
        for (ErrorMessage error : source.getErrors()) {
            destination.addError(new FieldError(
                    destination.getObjectName(),
                    null,
                    null
            ));
        }
    }

    @Override
    public BindingResult map(ValidationResult source) {
        return null;
    }

    @Override
    public ValidationResult reverse(BindingResult source) {
        throw new UnsupportedOperationException();
    }

}
