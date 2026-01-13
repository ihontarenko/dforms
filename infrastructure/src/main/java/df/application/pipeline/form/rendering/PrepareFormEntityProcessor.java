package df.application.pipeline.form.rendering;

import df.application.dto.form.FormDTO;
import org.jmouse.common.pipeline.context.DefaultPipelineContext;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import df.application.mapping.form.DeepFormMapper;
import df.application.persistence.entity.form.Form;

public class PrepareFormEntityProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Form entity = arguments.getArgument(Form.class);

        if (entity == null) {
            throw new FormRenderProcessorException(
                    "Form entry with the identifier '%s' was not found in the database."
                            .formatted(arguments.getRequiredArgument("PRIMARY_ID")));
        }

        if (arguments instanceof DefaultPipelineContext mutableArgumentsContext) {
            mutableArgumentsContext.setArgument(populateFormDTO(entity, context));
            mutableArgumentsContext.setArgument(entity);
        }

        return FormRenderReturnCode.PRE_BUILD_NODE_TREE;
    }

    private FormDTO populateFormDTO(Form entity, PipelineContext context) {
        return context.getBeanLookup().getBean(DeepFormMapper.class).map(entity);
    }

}
