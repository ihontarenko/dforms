package df.base.pipeline.dynamic_form;

import df.base.common.annotation.parsing.ParameterParser;
import df.base.common.annotation.parsing.ast.AnnotationParameterNode;
import df.base.common.context.ArgumentsContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.ast.Literal;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.validation.custom.*;
import df.base.dto.form.FieldConfigDTO;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static df.base.common.libs.jbm.ReflectionUtils.getClassFor;

public class InitializeValidatorsProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Map<String, List<FieldConfigDTO>> validationConfigs = arguments.requireArgument("VALIDATION_CONFIGS");
        Validation                        validation        = createValidation(context);

        validationConfigs.forEach((fieldName, configs) -> {
            for (FieldConfigDTO configDTO : configs) {
                String validatorParameters = configDTO.getValue();
                String configName          = configDTO.getKey();
                String validatorName       = configName.substring(configName.indexOf(':') + 1);

                Map<String, Object> parameters = normalizeParameters(
                        context.getBean(ParameterParser.class).parse(validatorParameters));

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
        ApplicationContext applicationContext = context.getProperty(ApplicationContext.class);
        MessageResolver    messageResolver    = context.getBean(MessageResolver.class);

        return new Validation("DYNAMIC_FORM_VALIDATION", messageResolver, applicationContext);
    }

    private Map<String, Object> normalizeParameters(Node root) {
        Map<String, Object> parameters = new HashMap<>();

        for (Node child : root.children()) {
            if (child instanceof AnnotationParameterNode parameterNode) {
                if (parameterNode.getValue() instanceof Literal literal) {
                    parameters.put(parameterNode.getKey(), literal.getValue());
                } else {
                    throw new IllegalArgumentException("Parameter values must be literals; other types are not allowed.");
                }
            }
        }

        return parameters;
    }

}
