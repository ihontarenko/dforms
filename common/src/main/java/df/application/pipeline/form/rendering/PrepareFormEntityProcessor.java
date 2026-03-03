package df.application.pipeline.form.rendering;

import df.application.dto.form.FormDTO;
import org.jmouse.common.dom.NodeContext;
import org.jmouse.dom.CorrectNodeDepth;
import org.jmouse.dom.Node;
import org.jmouse.dom.meterializer.DOMRenderingPipeline;
import org.jmouse.dom.meterializer.rules.FillActionMethod;
import org.jmouse.dom.renderer.RendererContext;
import org.jmouse.dom.renderer.Renderers;
import org.jmouse.dom.renderer.RenderingProcessor;
import org.jmouse.meterializer.ModelReference;
import org.jmouse.meterializer.Rendering;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.pipeline.PipelineProcessor;
import df.application.old_mapping.form.DeepFormMapper;
import df.application.persistence.entity.form.Form;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

import java.util.Map;

public class PrepareFormEntityProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        Form                         entity    = arguments.getArgument(Form.class);
        DOMRenderingPipeline         rendering = (DOMRenderingPipeline) context.getBeanLookup()
                .getBean(Rendering.class);
        FillActionMethod.RequestData data      = new FillActionMethod.RequestData(
                "/action/" + entity.getId(), "POST", Map.of());

        Node node = rendering.render(ModelReference.of(
                "default.form", entity
        ), r -> r.putAttribute(FillActionMethod.REQUEST_ATTRIBUTE, data));

        node.execute(new CorrectNodeDepth());

        RenderingProcessor engine = new RenderingProcessor(Renderers.html());
        String             html   = engine.render(node, RendererContext.defaultsHtmlPretty());

        if (entity == null) {
            throw new FormRenderProcessorException(
                    "Form entry with the identifier '%s' was not found in the database."
                            .formatted(arguments.getRequiredArgument("PRIMARY_ID")));
        }

        arguments.setArgument(FormDTO.class, populateFormDTO(entity, context));
        arguments.setArgument(Form.class, entity);

        return PipelineResult.of(FormRenderReturnCode.PRE_BUILD_NODE_TREE);
    }

    private FormDTO populateFormDTO(Form entity, PipelineContext context) {
        return context.getBeanLookup().getBean(DeepFormMapper.class).map(entity);
    }

}
