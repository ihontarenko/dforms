package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineArguments;

public interface PipelineProcessor {

    default Enum<?> process(PipelineContext context) throws Exception {
        return process(context, context.getPipelineArguments());
    }

    Enum<?> process(PipelineContext context, PipelineArguments arguments)
            throws Exception;

}
