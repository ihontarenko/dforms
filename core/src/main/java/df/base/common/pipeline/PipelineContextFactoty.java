package df.base.common.pipeline;

import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.common.pipeline.context.PipelineContext;

public class PipelineContextFactoty {

    public static PipelineContext createByDefault(Object... objects) {
        DefaultPipelineContext context = new DefaultPipelineContext();

        for (Object object : objects) {
            context.setProperty(object);
        }

        return context;
    }

}
