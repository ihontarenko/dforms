package df.base.pipeline.form.performing;

import df.base.common.context.ArgumentsContext;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.parser.ParameterParser;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.validation.custom.BasicValidators;
import df.base.common.validation.custom.Validation;
import df.base.common.validation.custom.Validator;
import df.base.common.validation.custom.ValidatorConstraintFactory;
import df.base.dto.form.FieldConfigDTO;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

import static df.base.common.libs.jbm.ReflectionUtils.getClassFor;

public class InitializeValidatorsProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Map<String, List<FieldConfigDTO>> validationConfigs = arguments.requireArgument("VALIDATION_CONFIGS");
        Validation                        validation        = createValidation(context);
        EvaluationContext                 evaluationContext = context.hasProperty(
                EvaluationContext.class) ? context.getProperty(EvaluationContext.class) : new EvaluationContext();

        // todo: think about binding some useful variables to EvaluationContext
        validationConfigs.forEach((fieldName, configs) -> {
            for (FieldConfigDTO configDTO : configs) {
                String validatorParameters = configDTO.getValue();
                String configName          = configDTO.getKey();
                String validatorName       = configName.substring(configName.indexOf(':') + 1);
                Node   root                = context.getBean(ParameterParser.class).parse(validatorParameters);

                Map<String, Object> parameters = normalizeParameters(root, evaluationContext);

                validation.addValidator(fieldName, resolveValidator(validatorName, parameters));
            }
        });

        context.setProperty(Validation.class, validation);

        return ReturnCodes.VALIDATE;
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

    private Validation createValidation(PipelineContext context) {
        return new Validation("DYNAMIC_FORM_VALIDATION", context.getProperty(ApplicationContext.class));
    }

    private Map<String, Object> normalizeParameters(Node root, EvaluationContext evaluationContext) {
        return (Map<String, Object>) root.first().evaluate(evaluationContext);
    }

}
