package df.base.pipeline.form;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.persistence.entity.form.Form;

import java.util.Optional;

public class FormUpdateProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        Optional<Form> optional = arguments.getArgument(Optional.class);

        if (optional.isPresent()) {
            return ReturnCode.UPDATE;
        }

        return ReturnCode.CREATE;
    }

    enum ReturnCode {
        CREATE, UPDATE
    }

}
