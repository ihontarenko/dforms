package df.application.pipeline.test;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.PipelineProcessor;
import df.common.context.ArgumentsContext;
import df.application.pipeline.DefaultReturnCode;
import df.application.service.form.FieldService;

public class Test3Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        context.getBean(FieldService.class);
        context.getArgumentsContext().requireArgument(Test2Processor.class).getClass();
        return DefaultReturnCode.CC;
    }

}
