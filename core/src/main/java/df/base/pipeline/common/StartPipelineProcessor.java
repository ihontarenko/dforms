package df.base.pipeline.common;

import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;
import df.base.pipeline.DefaultReturnCode;

import java.util.Random;

public class StartPipelineProcessor implements PipelineProcessor {

    private String field;
    private String contextClass;
    private String authUser;

    @Override
    public Enum<?> process(PipelineContext context) throws Exception {
        System.out.println("start pipe");
        return new Random().nextInt() % 2 == 0 ? DefaultReturnCode.BATCH_ITEMS : DefaultReturnCode.SINGLE_ITEM;
    }

}
