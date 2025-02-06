package df.application.pipeline.test;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import org.jmouse.common.support.context.ArgumentsContext;
import df.application.pipeline.DefaultReturnCode;

public class Test1Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        System.out.println(getClass());
        return DefaultReturnCode.AA;
    }

}
