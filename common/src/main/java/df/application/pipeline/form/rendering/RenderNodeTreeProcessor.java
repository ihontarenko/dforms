package df.application.pipeline.form.rendering;

import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.NodeContext;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.jmouse.core.context.result.MutableResultContext;

public class RenderNodeTreeProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        Node                 root   = arguments.getRequiredArgument(Node.class);
        MutableResultContext result = context.getResultContext();

        root.execute(NodeContext.REORDER_NODE_CORRECTOR);

        result.setReturnValue(root.interpret(context.getBeanLookup().getBean(NodeContext.class)));

        return PipelineResult.of(FormRenderReturnCode.FINISH);
    }

}
