package df.application.pipeline.form.rendering;

import org.jmouse.common.pipeline.PipelineResult;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class PostBuildPublicProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        return PipelineResult.of(FormRenderReturnCode.RENDER_NOTE_TREE);
    }

}
