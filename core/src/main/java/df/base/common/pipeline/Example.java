package df.base.common.pipeline;

import df.base.common.pipeline.context.PipelineContext;
import df.base.common.context.bean_provider.SpringBeanProvider;
import df.base.common.pipeline.context.DefaultPipelineContext;

public class Example {

    public static void main(String... arguments) throws Exception {
        PipelineContext ctx = new DefaultPipelineContext();

        ctx.setBeanProvider(new SpringBeanProvider());

        PipelineManager manager = new PipelineManager(ctx, "/pipeline/pipeline-DEFAULT.xml");

        manager.runPipeline("test-chain");

        System.out.println(ctx);
    }

}
