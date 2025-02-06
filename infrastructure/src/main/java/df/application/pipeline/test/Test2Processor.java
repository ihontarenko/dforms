package df.application.pipeline.test;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import org.jmouse.common.support.context.ArgumentsContext;

public class Test2Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        System.out.println(getClass());
        throw new RuntimeException("Error Occurred");
    }

}
