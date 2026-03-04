package df.application.pipeline.form.rendering;

import df.application.dto.form.FormDTO;
import df.application.provider.error.BindingResultErrorProvider;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.core.context.ArgumentsContext;
import org.jmouse.common.support.provider.data.DataProvider;
import org.jmouse.common.support.provider.data.MapDataProvider;
import org.jmouse.common.support.provider.error.ErrorDetailsProvider;
import org.jmouse.common.support.provider.error.ErrorProvider;
import org.jmouse.common.dom.Node;
import org.jmouse.common.dom.PostDataProvider;
import org.jmouse.dom.constructor.NodeConstructor;
import org.jmouse.dom.constructor.NodeConstructorContext;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.dom.constructor.AbstractNodeConstructorRegistry;
import df.application.html.forms.bootstrap.BootstrapBuilderRegistry;
import org.jmouse.core.context.execution.ExecutionContext;
import org.jmouse.core.context.execution.ExecutionContextHolder;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.springframework.validation.BindingResult;

import java.util.Collections;

public class PreBuildNodeTreeProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        NodeConstructorContext builderContext = new NodeConstructorContext();
        // todo: replace with execution context
        String             environment    = arguments.getArgument("ENV_NAME");
        ExecutionContext executionContext = ExecutionContextHolder.current();

        builderContext.setDataProvider(createPostDataProvider(arguments));

        builderContext.setRegistry(new BootstrapBuilderRegistry());

        AbstractNodeConstructorRegistry strategy = (AbstractNodeConstructorRegistry) builderContext.getRegistry();
        NodeConstructor<FormDTO>        builder  = strategy.getConstructor(FormDTO.class);

        arguments.setArgument(
                Node.class, builder.construct(arguments.getRequiredArgument(FormDTO.class), builderContext)
        );

        Enum<?> nextCode = (environment != null && environment.equalsIgnoreCase("DEMO"))
                ? FormRenderReturnCode.POST_BUILD_DEMO : FormRenderReturnCode.POST_BUILD_PUBLIC;

        return PipelineResult.of(nextCode);
    }

    private PostDataProvider createPostDataProvider(ArgumentsContext arguments) {
        return new PostDataProvider(createDataProvider(arguments), createErrorProvider(arguments));
    }

    private DataProvider<String, Object> createDataProvider(ArgumentsContext arguments) {
        return new MapDataProvider(arguments.getRequiredArgument("REQUEST_DATA"));
    }

    private ErrorProvider<String> createErrorProvider(ArgumentsContext arguments) {
        BindingResult bindingResult = arguments.getArgument(BindingResult.class);

        if (bindingResult == null) {
            return new ErrorDetailsProvider(Collections.emptyMap());
        }

        return new BindingResultErrorProvider(bindingResult);
    }

}
