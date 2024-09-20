package df.base.pipeline.test;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import df.base.pipeline.DefaultReturnCode;

public class Test1Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        System.out.println(getClass());
        return DefaultReturnCode.AA;
    }

}
