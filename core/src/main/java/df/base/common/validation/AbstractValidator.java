package df.base.common.validation;

import java.util.ArrayList;
import java.util.List;

import static df.base.common.jbm.StringUtils.underscored;

public abstract class AbstractValidator<T> implements Validator<T> {

    protected final List<Validator<T>>            children = new ArrayList<>();
    protected       ValidatorBundle.Configuration configuration;

    @Override
    public void initialize(ValidatorBundle.Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public ValidationResult validate(T value) {
        ValidationResult result = new ValidationResult();

        validate(value, result);

        if (!result.isValid()) {
            return result;
        }

        for (Validator<T> child : children) {
            child.validate(value, result);

            if (!result.isValid()) {
                break;
            }
        }

        return result;
    }

    @Override
    public void addChild(Validator<T> child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return "VALIDATOR: [%s] CHILDREN: [%d]"
                .formatted(underscored(getClass().getSimpleName(), true), children.size());
    }

}
