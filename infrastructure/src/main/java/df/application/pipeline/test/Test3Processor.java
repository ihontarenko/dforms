package df.application.pipeline.test;

import org.jmouse.common.pipeline.PipelineResult;
import org.jmouse.common.pipeline.context.PipelineContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import df.application.pipeline.DefaultReturnCode;
import df.application.service.form.FieldService;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class Test3Processor implements PipelineProcessor {

    @Override
    public PipelineResult process(
            PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous
    ) throws Exception {
        context.getBeanLookup().getBean(FieldService.class);
        context.getArgumentsContext().getRequiredArgument(Test2Processor.class).getClass();
        return PipelineResult.of(DefaultReturnCode.CC);
    }

}
