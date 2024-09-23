package df.base.pipeline.df.performing;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.validation.custom.Validation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

public class ValidateDynamicFormProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "dynamicForm");
        Validation    validation    = context.getProperty(Validation.class);

        context.setProperty(BindingResult.class, bindingResult);

        return ReturnCodes.VALIDATION_FAIL;
    }

}
