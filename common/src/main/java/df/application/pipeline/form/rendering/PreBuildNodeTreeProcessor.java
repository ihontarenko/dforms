package df.application.pipeline.form.rendering;

import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.form.FormConfig;
import org.jmouse.dom.CorrectNodeDepth;
import org.jmouse.dom.FormMetadata;
import org.jmouse.dom.Node;
import org.jmouse.dom.meterializer.DOMRenderingPipeline;
import org.jmouse.meterializer.ModelReference;
import org.jmouse.meterializer.Rendering;
import org.jmouse.meterializer.SubmissionState;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

import java.util.HashMap;
import java.util.Map;

public class PreBuildNodeTreeProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        Form                 entity      = arguments.getArgument(Form.class);
        String               environment = arguments.getArgument("ENV_NAME");
        DOMRenderingPipeline rendering   = (DOMRenderingPipeline) context.getBeanLookup().getBean(Rendering.class);

        Map<String, String> configs = new HashMap<>();

        for (FormConfig config : entity.getConfigs()) {
            configs.put(config.getConfigName(), config.getConfigValue());
        }

        FormMetadata         metadata    = FormMetadata.of(
                configs.get("CONFIG_FORM_ACTION"),
                configs.get("CONFIG_FORM_METHOD"),
                Map.of());

        SubmissionState submission = SubmissionState.of(
                Map.of("element_name", "R13"),
                Map.of("element_name", "Some error!")
        );

        Node node = rendering.render(ModelReference.of(
                "default.form", entity
        ), request -> request
                .setAttribute(FormMetadata.REQUEST_ATTRIBUTE, metadata)
                .setAttribute("submitCaption", configs.get("CONFIG_SUBMIT_TEXT"))
                .setAttribute(SubmissionState.REQUEST_ATTRIBUTE, submission)
        );

        node.execute(new CorrectNodeDepth());

        arguments.setArgument(Node.class, node);

        Enum<?> nextCode = (environment != null && environment.equalsIgnoreCase("DEMO"))
                ? FormRenderReturnCode.POST_BUILD_DEMO : FormRenderReturnCode.POST_BUILD_PUBLIC;

        return PipelineResult.of(nextCode);
    }

}
