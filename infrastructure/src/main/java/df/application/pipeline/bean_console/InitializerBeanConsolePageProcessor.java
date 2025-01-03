package df.application.pipeline.bean_console;

import df.application.pipeline.DefaultReturnCode;
import df.application.service.bc.ClassService;
import df.common.context.ArgumentsContext;
import df.common.pipeline.PipelineProcessor;
import df.common.pipeline.context.PipelineContext;

public class InitializerBeanConsolePageProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        ClassService classService = arguments.getArgument(ClassService.class);

        return DefaultReturnCode.AA;
    }

}
