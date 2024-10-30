package df.base.pipeline.form.rendering;

import df.base.common.context.ArgumentsContext;
import df.base.common.context.provider.data.DataProvider;
import df.base.common.context.provider.data.MapDataProvider;
import df.base.common.context.provider.error.BindingResultErrorProvider;
import df.base.common.context.provider.error.ErrorDetailsProvider;
import df.base.common.context.provider.error.ErrorProvider;
import df.base.common.elements.Node;
import df.base.common.elements.PostDataProvider;
import df.base.common.elements.builder.NodeBuilder;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.dto.form.FormDTO;
import df.base.common.elements.builder.AbstractBuilderStrategy;
import df.base.html.forms.bootstrap.BootstrapBuilderStrategy;
import org.springframework.validation.BindingResult;

import java.util.Collections;

public class PreBuildNodeTreeProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        NodeBuilderContext builderContext = new NodeBuilderContext();
        String             environment    = arguments.getArgument("ENV_NAME");

        builderContext.setDataProvider(createPostDataProvider(arguments));

        builderContext.setStrategy(new BootstrapBuilderStrategy());

        AbstractBuilderStrategy strategy = (AbstractBuilderStrategy) builderContext.getStrategy();
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
