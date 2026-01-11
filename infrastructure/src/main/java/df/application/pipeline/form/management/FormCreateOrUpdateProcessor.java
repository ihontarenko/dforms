package df.application.pipeline.form.management;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;

import java.util.Optional;

public class FormCreateOrUpdateProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return arguments.<Optional<?>>getRequiredArgument(Optional.class).isPresent()
                ? FormReturnCode.UPDATE : FormReturnCode.CREATE;
    }

}
