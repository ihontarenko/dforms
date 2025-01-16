package df.application.commands.validation;

import df.common.commans.CommandRequest;
import df.common.commans.annotation.Action;
import df.common.commans.annotation.Command;
import df.common.validation.custom.BasicValidators;
import df.common.validation.custom.Validator;
import df.common.validation.custom.ValidatorConstraintFactory;
import svit.reflection.Reflections;

import java.util.Map;

@Command("validation")
public class ValidatorResolverOperation {

    private Validator resolveValidator(String validatorName, Map<String, Object> parameters) {
        ValidatorConstraintFactory factory = ValidatorConstraintFactory.BASIC_FACTORY;
        Class<? extends Validator> validatorClass;

        if (validatorName.charAt(0) == '@') {
            validatorClass = (Class<? extends Validator>) Reflections.getClassFor(validatorName.substring(1));
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
