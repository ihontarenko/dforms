package df.base.pipeline.test;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.pipeline.DefaultReturnCode;

public class Test2Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        System.out.println(getClass());
        return DefaultReturnCode.BB;
    }

}
