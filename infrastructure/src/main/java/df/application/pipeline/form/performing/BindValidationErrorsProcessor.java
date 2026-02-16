package df.application.pipeline.form.performing;

import org.jmouse.common.pipeline.PipelineResult;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class BindValidationErrorsProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        return PipelineResult.of(ReturnCodes.FINISH);
    }
}
