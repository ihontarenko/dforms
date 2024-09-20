package df.base.common.pipeline;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.context.PipelineContext;

public interface PipelineProcessor {

    default Enum<?> process(PipelineContext context) throws Exception {
        return process(context, context.getArgumentsContext());
    }

    Enum<?> process(PipelineContext context, ArgumentsContext arguments)
            throws Exception;

}
