package df.application.pipeline.form.management;

import df.application.dto.form.FormDTO;
import df.application.persistence.entity.form.Form;
import df.application.service.form.FormService;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;
import org.jmouse.core.context.result.MutableResultContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FormUpdateProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormUpdateProcessor.class);

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        Optional<Form>       optional      = arguments.getRequiredArgument(Optional.class);
        FormDTO              formDTO       = arguments.getRequiredArgument(FormDTO.class);
        FormService          service       = arguments.getRequiredArgument(FormService.class);
        MutableResultContext resultContext = context.getResultContext();

        optional.ifPresent(form -> resultContext.setReturnValue(service.update(form, formDTO)));

        LOGGER.info("FORM '{}' UPDATED", formDTO.id());

        return PipelineResult.of(FormReturnCode.END);
    }

}
