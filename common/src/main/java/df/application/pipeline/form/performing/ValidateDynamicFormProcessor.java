package df.application.pipeline.form.performing;

import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.jmouse.validator.ValidationProcessor;
import org.jmouse.validator.ValidationResult;

import java.util.Map;

public class ValidateDynamicFormProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        Map<String, Object>                   requestData         = arguments.getArgument("REQUEST_DATA");
        ValidationProcessor                   validationProcessor = context.getRequiredValue(ValidationProcessor.class);
        ValidationResult<Map<String, Object>> validationResult    = validationProcessor.validate(requestData, "test");

        context.setValue(ValidationResult.class, validationResult);

        return PipelineResult.of(
                validationResult.hasErrors() ? ReturnCodes.VALIDATION_FAIL : ReturnCodes.VALIDATION_PASS
        );
    }

}
