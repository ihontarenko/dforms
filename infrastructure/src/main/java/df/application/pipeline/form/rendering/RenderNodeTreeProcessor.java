package df.application.pipeline.form.rendering;

import df.common.elements.Node;
import df.common.elements.NodeContext;
import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.context.ArgumentsContext;
import df.common.context.ResultContext;

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
