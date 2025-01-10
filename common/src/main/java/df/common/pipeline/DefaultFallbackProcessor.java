package df.common.pipeline;

import svit.context.ArgumentsContext;
import df.common.pipeline.context.PipelineContext;

public class DefaultFallbackProcessor implements PipelineProcessor{

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Throwable throwable = context.getProperty("EXCEPTION");
        throw new PipelineRuntimeException("ERROR DURING PIPELINE EXECUTION WITH CAUSE: [%s]"
                .formatted(throwable.getMessage()), throwable);
    }

}
