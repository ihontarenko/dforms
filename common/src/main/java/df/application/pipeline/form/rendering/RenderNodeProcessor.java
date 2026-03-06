package df.application.pipeline.form.rendering;

import org.jmouse.dom.Node;
import org.jmouse.dom.renderer.RendererContext;
import org.jmouse.dom.renderer.Renderers;
import org.jmouse.dom.renderer.RenderingProcessor;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class RenderNodeProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        Node               node   = arguments.getArgument(Node.class);
        RenderingProcessor engine = new RenderingProcessor(Renderers.html());
        String             html   = engine.render(node, RendererContext.defaultsHtmlPretty());

        context.getResultContext().setReturnValue(html);

        return PipelineResult.of(FormRenderReturnCode.PRINT_RESULT);
    }

}
