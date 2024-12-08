package df.base.common.pipeline;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.context.PipelineContext;

public class DefaultFallbackProcessor implements PipelineProcessor{

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Throwable throwable = context.getProperty("EXCEPTION");
        throw new PipelineRuntimeException("ERROR DURING PIPELINE EXECUTION WITH CAUSE: [%s]"
                .formatted(throwable.getMessage()), throwable);
    }

}
