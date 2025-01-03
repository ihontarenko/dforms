package df.common.pipeline;

import df.common.context.ArgumentsContext;
import df.common.pipeline.context.PipelineContext;

public interface PipelineProcessor {

    default Enum<?> process(PipelineContext context) throws Exception {
        return process(context, context.getArgumentsContext());
    }

    Enum<?> process(PipelineContext context, ArgumentsContext arguments)
            throws Exception;

}
