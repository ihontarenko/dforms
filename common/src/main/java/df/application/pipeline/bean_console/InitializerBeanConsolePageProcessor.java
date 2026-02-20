package df.application.pipeline.bean_console;

import df.application.pipeline.DefaultReturnCode;
import df.application.service.bc.ClassService;
import org.jmouse.pipeline.PipelineResult;
import org.jmouse.pipeline.PipelineProcessor;
import org.jmouse.pipeline.context.PipelineContext;
import org.jmouse.core.context.mutable.MutableArgumentsContext;

public class InitializerBeanConsolePageProcessor implements PipelineProcessor {

    @Override
    public PipelineResult process(PipelineContext context, MutableArgumentsContext arguments, PipelineResult previous) throws Exception {
        ClassService classService = arguments.getArgument(ClassService.class);

        return PipelineResult.of(DefaultReturnCode.AA);
    }

}
