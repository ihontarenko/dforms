package df.base.pipeline.form.management;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.common.context.ArgumentsContext;
import df.base.dto.form.FormDTO;
import df.base.persistence.entity.form.Form;
import df.base.service.form.FormService;
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
