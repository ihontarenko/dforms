package df.application.pipeline.form.management;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.context.ArgumentsContext;

import java.util.Optional;

public class FormCreateOrUpdateProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return arguments.<Optional<?>>requireArgument(Optional.class).isPresent()
                ? FormReturnCode.UPDATE : FormReturnCode.CREATE;
    }

}
