package df.base.common.pipeline;

import df.base.common.pipeline.context.DefaultPipelineContext;
import df.base.common.pipeline.context.PipelineArguments;

public class Example {

    public static void main(String... arguments) throws Exception {
        PipelineContext   ctx  = new DefaultPipelineContext();
        PipelineArguments args = ctx.getPipelineArguments();

        args.setArgument("order", new Object());

        PipelineManager manager = new PipelineManager(ctx, "/pipeline/pipeline-DEFAULT.xml");

        manager.runPipeline("default-chain");
    }

}
