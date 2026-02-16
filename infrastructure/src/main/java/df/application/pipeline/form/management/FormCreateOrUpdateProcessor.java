package df.application.pipeline.form.management;

import org.jmouse.common.pipeline.PipelineResult;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

import java.util.Optional;

public class FormCreateOrUpdateProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        return PipelineResult.of(arguments.<Optional<?>>getRequiredArgument(Optional.class).isPresent()
                                         ? FormReturnCode.UPDATE : FormReturnCode.CREATE);
    }
}
