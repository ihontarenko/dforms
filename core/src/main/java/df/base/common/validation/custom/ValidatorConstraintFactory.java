package df.base.common.validation.custom;

import df.base.common.validation.custom.constraint.NotNullValidator;

import java.util.HashMap;
import java.util.Map;

public class ValidatorConstraintFactory {

    public static final ValidatorConstraintFactory BASIC_FACTORY = new ValidatorConstraintFactory();

    static {
        BASIC_FACTORY.addValidator(BasicValidators.NOT_NULL, new NotNullValidator());
        BASIC_FACTORY.addValidator(BasicValidators.SIZE, new NotNullValidator());
    }

    private final Map<Enum<?>, Validator>                  validators       = new HashMap<>();
    private final Map<Enum<?>, Class<? extends Validator>> validatorClasses = new HashMap<>();

    public void addValidator(Enum<?> key, Class<? extends Validator> validatorClass) {
        validatorClasses.put(key, validatorClass);
    }

    public void addValidator(Enum<?> key, Validator validator) {
        validators.put(key, validator);
    }

    public Validator getValidator(Enum<?> key) {
        Validator validator = validators.get(key);

        if (validator == null) {
            throw new ValidatorNotFoundException(
                    "The validator with key '%s' not registered for ValidatorConstraintFactory"
                            .formatted(key));
        }

        return validator;
    }

    public Class<? extends Validator> getValidatorClass(Enum<?> key) {
        Class<? extends Validator> validatorClass = validatorClasses.get(key);

        if (validatorClass == null) {
            throw new ValidatorNotFoundException(
                    "The validator class with key '%s' not registered for ValidatorConstraintFactory"
                            .formatted(key));
        }

        return validatorClass;
    }

    public Validator createNewValidator(Enum<?> key) {
        return null;
    }

}
