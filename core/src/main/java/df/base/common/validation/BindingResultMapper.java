package df.base.common.validation;

import df.base.mapper.Mapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public final class BindingResultMapper implements Mapper<List<ErrorMessage>, BindingResult> {

    @Override
    public void map(List<ErrorMessage> source, BindingResult destination) {
        for (ErrorMessage error : source) {
            destination.addError(new FieldError(
                    destination.getObjectName(),
                    error.pointer(),
                    error.message()
            ));
        }
    }

    @Override
    public BindingResult map(List<ErrorMessage> source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ErrorMessage> reverse(BindingResult source) {
        throw new UnsupportedOperationException();
    }

}
