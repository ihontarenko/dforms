package df.application.pipeline.form.rendering;

import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.NodeContext;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import org.jmouse.core.context.result.MutableResultContext;

public class RenderNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Node                 root   = arguments.getRequiredArgument(Node.class);
        MutableResultContext result = context.getResultContext();

        root.execute(NodeContext.REORDER_NODE_CORRECTOR);

        result.setReturnValue(root.interpret(context.getBeanLookup().getBean(NodeContext.class)));

        return FormRenderReturnCode.FINISH;
    }

}
