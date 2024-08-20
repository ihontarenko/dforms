package df.base.common.validation.builder;

import df.base.common.validation.ValidatorHolder;
import df.base.common.validation.Validator;
import df.base.common.validation.Configuration;
import df.base.common.validation.ValidatorException;

import java.util.List;

public class ValidationBuilder implements Builder<ValidatorHolder, Configuration.Collection> {

    @Override
    public ValidatorHolder build() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidatorHolder build(Configuration.Collection source) {
        ValidatorHolder     holder         = new ValidatorHolder(source.getName());
        List<Configuration> configurations = source.getValidators();

        for (Configuration configuration : configurations) {
            holder.addValidator(createValidator(configuration));
        }

        return holder;
    }

    @SuppressWarnings({"unchecked"})
    private Validator<Object> createValidator(Configuration config) {
        try {
            Class<?> validatorClass = Class.forName(config.getValidator());
            Validator<Object> validator = (Validator<Object>) validatorClass
                    .getDeclaredConstructor().newInstance();

            validator.initialize(config);

            return validator;
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO CREATE VALIDATOR INSTANCE", e);
        }
    }

}
