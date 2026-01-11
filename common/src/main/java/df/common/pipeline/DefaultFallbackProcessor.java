package df.common.pipeline;

import df.common.pipeline.context.PipelineContext;
import org.jmouse.core.context.ArgumentsContext;

public class DefaultFallbackProcessor implements PipelineProcessor{

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Throwable throwable = context.getValue("EXCEPTION");
        throw new PipelineRuntimeException("ERROR DURING PIPELINE EXECUTION WITH CAUSE: [%s]"
                .formatted(throwable.getMessage()), throwable);
    }

}
