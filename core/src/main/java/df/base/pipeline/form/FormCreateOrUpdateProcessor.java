package df.base.pipeline.form;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;

import java.util.Optional;

public class FormCreateOrUpdateProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        return arguments.<Optional<?>>requireArgument(Optional.class).isPresent()
                ? FormReturnCode.UPDATE : FormReturnCode.CREATE;
    }

}