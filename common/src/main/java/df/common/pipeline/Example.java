package df.common.pipeline;

import df.common.pipeline.context.PipelineContext;
import svit.provider.bean.SpringBeanProvider;
import df.common.pipeline.context.DefaultPipelineContext;

public class Example {

    public static void main(String... arguments) throws Exception {
        PipelineContext ctx = new DefaultPipelineContext();

        ctx.setBeanProvider(new SpringBeanProvider());

        PipelineManager manager = new PipelineManager("/pipeline/pipeline-DEFAULT.xml");

        manager.runPipeline("test-chain", ctx);

        System.out.println(ctx);
    }

}
