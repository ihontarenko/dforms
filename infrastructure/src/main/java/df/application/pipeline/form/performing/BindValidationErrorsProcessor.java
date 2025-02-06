package df.application.pipeline.form.performing;

import org.jmouse.common.support.context.ArgumentsContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;

public class BindValidationErrorsProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return ReturnCodes.FINISH;
    }

}
