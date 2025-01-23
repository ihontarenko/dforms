package df.application.pipeline.form.rendering;

import df.application.dto.form.FormDTO;
import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import svit.support.context.ArgumentsContext;
import df.application.mapping.form.DeepFormMapper;
import df.application.persistence.entity.form.Form;

public class PrepareFormEntityProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Form entity = arguments.getArgument(Form.class);

        if (entity == null) {
            throw new FormRenderProcessorException(
                    "Form entry with the identifier '%s' was not found in the database."
                            .formatted(arguments.requireArgument("PRIMARY_ID")));
        }

        arguments.setArgument(populateFormDTO(entity, context));
        arguments.setArgument(entity);

        return FormRenderReturnCode.PRE_BUILD_NODE_TREE;
    }

    private FormDTO populateFormDTO(Form entity, PipelineContext context) {
        return context.getBean(DeepFormMapper.class).map(entity);
    }

}
