package df.application.pipeline.form.management;

import df.application.dto.form.FormDTO;
import df.application.persistence.entity.user.User;
import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.DefaultPipelineContext;
import svit.context.ArgumentsContext;
import df.application.service.form.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormCreateProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormCreateProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        FormDTO     formDTO = arguments.requireArgument(FormDTO.class);
        FormService service = arguments.requireArgument(FormService.class);

        ((DefaultPipelineContext) context).setReturnValue(
                service.create(arguments.getArgument(User.class), formDTO)
        );

        LOGGER.info("FORM '{}' CREATED", formDTO.id());

        return FormReturnCode.POST_PERSISTENCE;
    }

}
