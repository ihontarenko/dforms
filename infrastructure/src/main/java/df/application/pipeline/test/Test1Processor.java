package df.application.pipeline.test;

import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.core.context.ArgumentsContext;
import df.application.pipeline.DefaultReturnCode;

public class Test1Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        System.out.println(getClass());
        return DefaultReturnCode.AA;
    }

}
