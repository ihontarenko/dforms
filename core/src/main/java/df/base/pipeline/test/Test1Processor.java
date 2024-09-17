package df.base.pipeline.test;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.pipeline.DefaultReturnCode;
import df.base.pipeline.form.FormReturnCode;

import java.util.Optional;

public class Test1Processor implements PipelineProcessor {

    @Override
    public Enum<?> process(PipelineContext context, PipelineArguments arguments) throws Exception {
        System.out.println(getClass());
        return DefaultReturnCode.AA;
    }

}
