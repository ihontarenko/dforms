package df.base.pipeline.form_render;

import df.base.common.elements.Node;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import df.base.dto.form.FormDTO;
import df.base.html.builder.AbstractBuilderStrategy;
import df.base.html.builder.bootstrap.BootstrapBuilderStrategy;

import static df.base.pipeline.form_render.FormRenderReturnCode.POST_BUILD_DEMO;
import static df.base.pipeline.form_render.FormRenderReturnCode.POST_BUILD_PUBLIC;

public class PreBuildNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        NodeBuilderContext builderContext = new NodeBuilderContext();
        String             environment    = arguments.getArgument("ENV_NAME");

        builderContext.setStrategy(new BootstrapBuilderStrategy());

        AbstractBuilderStrategy strategy = (AbstractBuilderStrategy) builderContext.getStrategy();
        NodeBuilder<FormDTO>    builder  = strategy.getBuilder(FormDTO.class);

        arguments.setArgument(Node.class, builder.build(arguments.requireArgument(FormDTO.class), builderContext));

        return (environment != null && environment.equals("DEMO")) ? POST_BUILD_DEMO : POST_BUILD_PUBLIC;
    }

}
