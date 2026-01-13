package df.application.pipeline.form.management;

import df.application.dto.form.FormDTO;
import df.application.persistence.entity.user.User;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import df.application.service.form.FormService;
import org.jmouse.core.context.result.MutableResultContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormCreateProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormCreateProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        FormDTO              formDTO       = arguments.getRequiredArgument(FormDTO.class);
        FormService          service       = arguments.getRequiredArgument(FormService.class);
        MutableResultContext resultContext = context.getResultContext();

        resultContext.setReturnValue(
                service.create(arguments.getArgument(User.class), formDTO)
        );

        LOGGER.info("FORM '{}' CREATED", formDTO.id());

        return FormReturnCode.POST_PERSISTENCE;
    }

}
