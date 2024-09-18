package df.base.common.pipeline;

import df.base.common.pipeline.context.DefaultPipelineContext;

public class Example {

    public static void main(String... arguments) throws Exception {
        PipelineContext   ctx  = new DefaultPipelineContext();

        PipelineManager manager = new PipelineManager(ctx, "/pipeline/pipeline-DEFAULT.xml");

        manager.runPipeline("test-chain");

        System.out.println(ctx);
    }

}
