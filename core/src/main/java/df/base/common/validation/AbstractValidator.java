package df.base.common.validation;

import static df.base.common.jbm.StringUtils.underscored;

public abstract class AbstractValidator<T> implements Validator<T> {

    protected Configuration configuration;

    @Override
    public void initialize(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "VALIDATOR: [%s]".formatted(underscored(getClass().getSimpleName(), true));
    }

}
