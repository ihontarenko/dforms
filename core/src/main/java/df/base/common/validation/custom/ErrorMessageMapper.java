package df.base.common.validation.custom;

import df.base.common.support.Mapper;
import df.base.property.ValidationMessages;

public class ErrorMessageMapper implements Mapper<ValidationMessages.Message, ErrorMessage> {

    @Override
    public ErrorMessage map(ValidationMessages.Message source) {
        return new ErrorMessage(source.getCode(), source.getMessage(), source.getPointer());
    }

    @Override
    public ValidationMessages.Message reverse(ErrorMessage source) {
        throw new UnsupportedOperationException();
    }

}
