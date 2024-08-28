package df.base.internal.validation;

import df.base.internal.Patcher;
import df.base.property.ValidationMessages;

import static java.util.Objects.requireNonNullElse;

public class ErrorMessagePatcher implements Patcher<ErrorMessage, ErrorMessage, ValidationMessages.Message> {

    @Override
    public ErrorMessage patch(ErrorMessage actual, ValidationMessages.Message patch) {
        return new ErrorMessage(
                requireNonNullElse(patch.getCode(), actual.code()),
                requireNonNullElse(patch.getMessage(), actual.message()),
                requireNonNullElse(patch.getPointer(), actual.pointer()));
    }

}
