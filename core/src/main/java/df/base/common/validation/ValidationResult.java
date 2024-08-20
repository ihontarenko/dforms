package df.base.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<ErrorMessage> errors = new ArrayList<>();

    public boolean isValid() {
        return errors.size() == 0;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public void addError(ErrorMessage error) {
        errors.add(error);
    }

}
