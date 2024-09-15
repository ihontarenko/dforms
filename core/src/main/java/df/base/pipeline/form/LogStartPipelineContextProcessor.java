package df.base.pipeline.form;

import df.base.common.elements.node.ElementNode;
import df.base.common.pipeline.PipelineChain;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;

public class LogStartPipelineContextProcessor implements PipelineProcessor {

    private String requiredFields;

    @Override
    public void process(PipelineContext context, PipelineChain chain) throws Exception {
        System.out.println("processor: " + getClass() + " -> " + context.getProperty(ElementNode.class));
        System.out.println("processor: " + getClass() + " -> " + getRequiredFields());
        chain.proceed(context);
    }

    public String getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(String requiredFields) {
        this.requiredFields = requiredFields;
    }
}
