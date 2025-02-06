package df.application.pipeline.form.rendering;

import df.application.dto.form.FormDTO;
import df.common.provider.error.BindingResultErrorProvider;
import org.jmouse.common.support.context.ArgumentsContext;
import org.jmouse.common.support.provider.data.DataProvider;
import org.jmouse.common.support.provider.data.MapDataProvider;
import org.jmouse.common.support.provider.error.ErrorDetailsProvider;
import org.jmouse.common.support.provider.error.ErrorProvider;
import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.PostDataProvider;
import org.jmouse.common.dom.builder.NodeBuilder;
import org.jmouse.common.dom.builder.NodeBuilderContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;
import org.jmouse.common.dom.builder.AbstractBuilderRegistry;
import df.application.html.forms.bootstrap.BootstrapBuilderRegistry;
import org.springframework.validation.BindingResult;

import java.util.Collections;

public class PreBuildNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        NodeBuilderContext builderContext = new NodeBuilderContext();
        String             environment    = arguments.getArgument("ENV_NAME");

        builderContext.setDataProvider(createPostDataProvider(arguments));

        builderContext.setRegistry(new BootstrapBuilderRegistry());

        AbstractBuilderRegistry strategy = (AbstractBuilderRegistry) builderContext.getRegistry();
        NodeBuilder<FormDTO>    builder  = strategy.getBuilder(FormDTO.class);

        arguments.setArgument(Node.class, builder.build(arguments.requireArgument(FormDTO.class), builderContext));

        return (environment != null && environment.equals("DEMO")) ? FormRenderReturnCode.POST_BUILD_DEMO : FormRenderReturnCode.POST_BUILD_PUBLIC;
    }

    private PostDataProvider createPostDataProvider(ArgumentsContext arguments) {
        return new PostDataProvider(createDataProvider(arguments), createErrorProvider(arguments));
    }

    private DataProvider<String, Object> createDataProvider(ArgumentsContext arguments) {
        return new MapDataProvider(arguments.requireArgument("REQUEST_DATA"));
    }

    private ErrorProvider<String> createErrorProvider(ArgumentsContext arguments) {
        BindingResult bindingResult = arguments.getArgument(BindingResult.class);

        if (bindingResult == null) {
            return new ErrorDetailsProvider(Collections.emptyMap());
        }

        return new BindingResultErrorProvider(bindingResult);
    }

}
