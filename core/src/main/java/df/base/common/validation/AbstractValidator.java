package df.base.common.validation;

import df.base.common.jbm.ClassUtils;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.jbm.StringUtils.underscored;

public abstract class AbstractValidator<T> implements Validator<T> {

    protected Configuration configuration;

    @Override
    public void initialize(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Map<String, String> configurationMap() {
        return new HashMap<>() {{
            put("errorCode", configuration.getErrorCode());
            put("pointer", configuration.getPointer());
            put("validatorClass", ClassUtils.getShortName(getClass()));
        }};
    }

    @Override
    public String toString() {
        return "VALIDATOR: [%s]".formatted(underscored(getClass().getSimpleName(), true));
    }

}
