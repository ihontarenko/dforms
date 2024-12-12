package df.base.pipeline.form.performing;

import df.base.common.commans.CommandExecutionContext;
import df.base.common.commans.CommandsManager;
import df.base.common.commans.CommandsManagerFactory;
import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.validation.custom.Validation;
import df.base.dto.form.FieldConfigDTO;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

import static df.base.common.commans.CommandExecutionContext.create;

public class InitializeValidatorsProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Map<String, List<FieldConfigDTO>> validationConfigs = arguments.requireArgument("VALIDATION_CONFIGS");
        Validation                        validation        = createValidation(context);
        CommandsManager                   commandsManager   = CommandsManagerFactory.create();

        validationConfigs.forEach((fieldName, configs) -> {
            for (FieldConfigDTO configDTO : configs) {
                validation.addValidator(fieldName, commandsManager.execute(
                        configDTO.getKey(), configDTO.getValue(), create("config", configDTO)));
            }
        });

        context.setProperty(Validation.class, validation);

        return ReturnCodes.VALIDATE;
    }

    private Validation createValidation(PipelineContext context) {
        return new Validation("DYNAMIC_FORM_VALIDATION", context.getProperty(ApplicationContext.class));
    }

}
