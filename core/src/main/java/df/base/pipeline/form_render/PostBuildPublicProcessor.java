package df.base.pipeline.form_render;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;

public class PostBuildPublicProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        return FormRenderReturnCode.RENDER_NOTE_TREE;
    }

}
