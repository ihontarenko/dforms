package df.base.pipeline.form_render;

import df.base.common.elements.Node;
import df.base.common.elements.NodeContext;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import df.base.common.context.ResultContext;

public class RenderNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Node          root   = arguments.requireArgument(Node.class);
        ResultContext result = context.getResultContext();

        root.execute(NodeContext.REORDER_NODE_CORRECTOR);

        result.setReturnValue(root.interpret(context.getBean(NodeContext.class)));

        return FormRenderReturnCode.FINISH;
    }

}
