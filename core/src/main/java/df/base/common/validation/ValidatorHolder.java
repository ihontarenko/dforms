package df.base.common.validation;

import java.util.List;

public class ValidatorHolder implements Validator {

    private final String                  name;
    private       List<Validator<Object>> validators;

    public ValidatorHolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Validator<Object>> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator<Object>> validators) {
        this.validators = validators;
    }

    public void addValidator(Validator<Object> validator) {
        this.validators.add(validator);
    }

}
