package df.base.pipeline.form.rendering;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import df.base.dto.form.FormDTO;
import df.base.mapping.form.DeepFormMapper;
import df.base.persistence.entity.form.Form;

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
