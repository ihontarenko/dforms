package df.base.pipeline;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.pipeline.form_render.FormRenderReturnCode;

public class PrintResultValueProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        System.out.println((String) context.getPipelineResult().getReturnValue());
        return FormRenderReturnCode.END;
    }

}
