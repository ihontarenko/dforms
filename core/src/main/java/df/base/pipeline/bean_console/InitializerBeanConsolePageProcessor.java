package df.base.pipeline.bean_console;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.pipeline.DefaultReturnCode;
import df.base.service.bc.ClassService;

public class InitializerBeanConsolePageProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        ClassService classService = arguments.getArgument(ClassService.class);

        return DefaultReturnCode.AA;
    }

}
