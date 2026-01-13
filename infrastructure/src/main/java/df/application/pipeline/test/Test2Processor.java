package df.application.pipeline.test;

import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;

public class Test2Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        System.out.println(getClass());
        throw new RuntimeException("Error Occurred");
    }

}
