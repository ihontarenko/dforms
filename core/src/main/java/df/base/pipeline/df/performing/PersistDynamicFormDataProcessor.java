package df.base.pipeline.df.performing;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;

public class PersistDynamicFormDataProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        return ReturnCodes.FINISH;
    }

}
