package df.base.pipeline.form.performing;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;

public class BindValidationErrorsProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return ReturnCodes.FINISH;
    }

}