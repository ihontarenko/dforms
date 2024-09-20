package df.base.pipeline.form;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.common.context.ArgumentsContext;
import df.base.dto.form.FormDTO;
import df.base.persistence.entity.user.User;
import df.base.service.form.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormCreateProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormCreateProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        FormDTO        formDTO  = arguments.requireArgument(FormDTO.class);
        FormService    service  = arguments.requireArgument(FormService.class);

        ((DefaultPipelineContext) context).setReturnValue(
                service.create(arguments.getArgument(User.class), formDTO)
        );

        LOGGER.info("FORM '{}' CREATED", formDTO.id());

        return FormReturnCode.POST_PERSISTENCE;
    }

}
