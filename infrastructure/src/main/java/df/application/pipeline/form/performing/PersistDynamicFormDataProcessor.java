package df.application.pipeline.form.performing;

import org.jmouse.common.support.context.ArgumentsContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;

import java.util.Map;

public class PersistDynamicFormDataProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        Map<String, Object> requestData = arguments.getArgument("REQUEST_DATA");

        return ReturnCodes.FINISH;
    }

}
