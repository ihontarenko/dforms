package df.application.commands.validation;

import df.common.commans.CommandRequest;
import df.common.commans.annotation.Action;
import df.common.commans.annotation.Command;
import org.jmouse.core.reflection.Reflections;
import org.jmouse.validator.old.BasicValidators;
import org.jmouse.validator.old.Validator;
import org.jmouse.validator.old.ValidatorConstraintFactory;

import java.util.Map;

@Command("validation")
public class ValidatorResolverOperation {

    private org.jmouse.validator.old.Validator resolveValidator(String validatorName, Map<String, Object> parameters) {
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
