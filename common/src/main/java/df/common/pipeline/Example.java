package df.common.pipeline;

import df.common.pipeline.context.PipelineContext;
import df.common.pipeline.context.DefaultPipelineContext;
import df.common.provider.bean.SpringBeanProvider;

public class Example {

    public static void main(String... arguments) throws Exception {
        PipelineContext ctx = new DefaultPipelineContext();

        ctx.setBeanProvider(new SpringBeanProvider());

        PipelineManager manager = new PipelineManager("/pipeline/pipeline-DEFAULT.xml");

        manager.runPipeline("test-chain", ctx);

        System.out.println(ctx);
    }

}
