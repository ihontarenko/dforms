package df.base.commands.validation;

import df.base.common.commans.CommandRequest;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;
import df.base.common.validation.custom.BasicValidators;
import df.base.common.validation.custom.Validator;
import df.base.common.validation.custom.ValidatorConstraintFactory;

import java.util.Map;

import static df.base.common.container.ReflectionUtils.getClassFor;

@Command("validation")
public class ValidatorResolverOperation {

    private Validator resolveValidator(String validatorName, Map<String, Object> parameters) {
        ValidatorConstraintFactory factory = ValidatorConstraintFactory.BASIC_FACTORY;
        Class<? extends Validator> validatorClass;

        if (validatorName.charAt(0) == '@') {
            validatorClass = (Class<? extends Validator>) getClassFor(validatorName.substring(1));
        } else {
            BasicValidators validatorType = BasicValidators.valueOf(validatorName.toUpperCase());
            validatorClass = factory.getValidatorClass(validatorType);
        }

        return factory.createNewValidator(validatorClass, parameters);
    }

    @Action({"not_null", "empty_string", "range", "custom",})
    public Validator createValidator(String action, CommandRequest request) {
        return resolveValidator(action, request.queryParameters());
    }

}
