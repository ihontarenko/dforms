package df.application.pipeline.form.rendering;

import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;

public class PostBuildPublicProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return FormRenderReturnCode.RENDER_NOTE_TREE;
    }

}
