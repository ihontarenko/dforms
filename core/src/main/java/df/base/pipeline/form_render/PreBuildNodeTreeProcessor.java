package df.base.pipeline.form_render;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;

public class PreBuildNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        // todo: if necessary modify nodes for DEMO mode BUILD_DEMO_NODE_TREE
        return FormRenderReturnCode.RENDER_NOTE_TREE;
    }

}
