package df.base.pipeline.form_render;

import df.base.common.elements.Node;
import df.base.common.elements.NodeContext;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.common.pipeline.context.PipelineResult;

public class RenderNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        Node           root   = arguments.requireArgument(Node.class);
        PipelineResult result = context.getPipelineResult();

        root.execute(NodeContext.REORDER_NODE_CORRECTOR);

        result.setReturnValue(root.interpret(context.getBean(NodeContext.class)));

        return FormRenderReturnCode.END;
    }

}
