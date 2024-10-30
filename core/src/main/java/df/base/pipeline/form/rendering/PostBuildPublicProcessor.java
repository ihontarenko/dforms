package df.base.pipeline.form.rendering;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;

public class PostBuildPublicProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return FormRenderReturnCode.RENDER_NOTE_TREE;
    }

}
