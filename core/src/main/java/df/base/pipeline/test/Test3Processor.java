package df.base.pipeline.test;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.pipeline.DefaultReturnCode;
import df.base.service.form.FieldService;

public class Test3Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        context.getBean(FieldService.class);
        context.getPipelineArguments().requireArgument(Test2Processor.class).getClass();
        return DefaultReturnCode.CC;
    }

}
