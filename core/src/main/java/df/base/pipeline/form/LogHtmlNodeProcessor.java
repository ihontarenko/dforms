package df.base.pipeline.form;

import df.base.common.elements.node.ElementNode;
import df.base.common.pipeline.PipelineChain;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;

public class LogHtmlNodeProcessor implements PipelineProcessor {

    @Override
    public void process(PipelineContext context, PipelineChain chain) throws Exception {
        System.out.println("processor: " + getClass() + " -> " + context.getProperty(ElementNode.class));
        chain.proceed(context);
    }

}
