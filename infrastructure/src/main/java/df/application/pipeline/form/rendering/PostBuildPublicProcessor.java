package df.application.pipeline.form.rendering;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.context.ArgumentsContext;

public class PostBuildPublicProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return FormRenderReturnCode.RENDER_NOTE_TREE;
    }

}
