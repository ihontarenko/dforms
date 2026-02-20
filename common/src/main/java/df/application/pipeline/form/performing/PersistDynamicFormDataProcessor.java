package df.application.pipeline.form.performing;

import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

import java.util.Map;

public class PersistDynamicFormDataProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        Map<String, Object> requestData = arguments.getArgument("REQUEST_DATA");

        return PipelineResult.finish();
    }

}
