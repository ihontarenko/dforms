package df.application.pipeline.bean_console;

import df.application.pipeline.DefaultReturnCode;
import df.application.service.bc.ClassService;
import org.jmouse.core.context.ArgumentsContext;
import org.jmouse.common.pipeline.PipelineProcessor;
import org.jmouse.common.pipeline.context.PipelineContext;

public class InitializerBeanConsolePageProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        ClassService classService = arguments.getArgument(ClassService.class);

        return DefaultReturnCode.AA;
    }

}
