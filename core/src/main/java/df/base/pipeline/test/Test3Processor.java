package df.base.pipeline.test;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.context.ArgumentsContext;
import df.base.pipeline.DefaultReturnCode;
import df.base.service.form.FieldService;

public class Test3Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, ArgumentsContext arguments) throws Exception {
        context.getBean(FieldService.class);
        context.getArgumentsContext().requireArgument(Test2Processor.class).getClass();
        return DefaultReturnCode.CC;
    }

}
