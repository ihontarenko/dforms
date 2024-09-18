package df.base.pipeline.form_render;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;

public class PrepareFormEntityProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        return FormRenderReturnCode.PRE_BUILD_NODE_TREE;
    }

}
