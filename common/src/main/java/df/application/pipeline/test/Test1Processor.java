package df.application.pipeline.test;

import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.pipeline.PipelineProcessor;
import df.application.pipeline.DefaultReturnCode;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class Test1Processor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        System.out.println(getClass());
        return PipelineResult.of(DefaultReturnCode.AA);
    }

}
