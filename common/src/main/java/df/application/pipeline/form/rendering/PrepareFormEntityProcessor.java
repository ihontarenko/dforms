package df.application.pipeline.form.rendering;

import df.application.dto.form.FormDTO;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.pipeline.PipelineProcessor;
import df.application.old_mapping.form.DeepFormMapper;
import df.application.persistence.entity.form.Form;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class PrepareFormEntityProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        Form entity = arguments.getArgument(Form.class);

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
