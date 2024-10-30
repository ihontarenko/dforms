package df.base.pipeline.bean_console;

import df.base.common.context.ArgumentsContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineContext;
import df.base.pipeline.DefaultReturnCode;

public class InitializerBeanConsolePageProcessor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {

        return DefaultReturnCode.AA;
    }

}
