package df.base.operation.validation;

import df.base.common.operation.OperationRequest;
import df.base.common.operation.Operator;
import df.base.common.operation.annotation.Operation;
import df.base.common.validation.custom.BasicValidators;
import df.base.common.validation.custom.Validator;
import df.base.common.validation.custom.ValidatorConstraintFactory;

import java.util.Map;

import static df.base.common.libs.jbm.ReflectionUtils.getClassFor;

@Operation(operation = "validation", actions = {
        @Operation.Action("not_null"),
        @Operation.Action("empty_string"),
        @Operation.Action("range"),
        @Operation.Action("custom"),
})
public class ValidatorResolverOperation implements Operator<Validator> {

    @Override
    public Validator handle(OperationRequest request) {
        return resolveValidator(request.action(), request.parameters());
    }

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

}
