package df.application.pipeline.test;

import org.jmouse.common.pipeline.PipelineResult;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class Test2Processor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        System.out.println(getClass());
        throw new RuntimeException("Error Occurred");
    }

}
