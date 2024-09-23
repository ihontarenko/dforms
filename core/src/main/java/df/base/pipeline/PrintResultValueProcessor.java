package df.base.pipeline;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import df.base.pipeline.df.rendering.FormRenderReturnCode;

public class PrintResultValueProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        System.out.println((String) context.getResultContext().getReturnValue());
        return FormRenderReturnCode.END;
    }

}
