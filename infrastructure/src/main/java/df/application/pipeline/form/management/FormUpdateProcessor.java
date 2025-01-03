package df.application.pipeline.form.management;

import df.application.dto.form.FormDTO;
import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.DefaultPipelineContext;
import df.common.context.ArgumentsContext;
import df.application.persistence.entity.form.Form;
import df.application.service.form.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class FormUpdateProcessor implements PipelineProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormUpdateProcessor.class);

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Optional<Form> optional = arguments.requireArgument(Optional.class);
        FormDTO        formDTO  = arguments.requireArgument(FormDTO.class);
        FormService    service  = arguments.requireArgument(FormService.class);

        ((DefaultPipelineContext) context).setReturnValue(
                service.update(optional.get(), formDTO)
        );

        LOGGER.info("FORM '{}' UPDATED", formDTO.id());

        return FormReturnCode.END;
    }

}
