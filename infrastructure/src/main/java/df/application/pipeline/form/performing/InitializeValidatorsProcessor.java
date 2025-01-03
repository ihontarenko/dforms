package df.application.pipeline.form.performing;

import df.application.Instances;
import df.application.dto.form.FieldConfigDTO;
import df.common.commans.CommandsManager;
import df.common.commans.CommandsManagerFactory;
import df.common.context.ArgumentsContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;
import df.common.validation.custom.Validation;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

import static df.common.commans.CommandExecutionContext.create;

public class InitializeValidatorsProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Map<String, List<FieldConfigDTO>> validationConfigs = arguments.requireArgument("VALIDATION_CONFIGS");
        Validation                        validation        = createValidation(context);
        CommandsManager                   commandsManager   = Instances.COMMANDS_MANAGER;

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
