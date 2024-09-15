package df.base.pipeline.form;

import df.base.common.pipeline.PipelineChain;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;

public class LogEndPipelineContextProcessor implements PipelineProcessor {

    @Override
    public void process(PipelineContext context, PipelineChain chain) throws Exception {
        System.out.println("processor: " + getClass() + " -> " + context);
        chain.proceed(context);
    }

}
