package df.base.pipeline.common;

import df.base.common.elements.node.ElementNode;
import df.base.common.pipeline.PipelineChain;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;

public class SingleItemProcessor implements PipelineProcessor {

    private String requiredFields;

    @Override
    public Enum<?> process(PipelineContext context) throws Exception {
        System.out.println("single item processor");
        return null;
    }

    public String getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(String requiredFields) {
        this.requiredFields = requiredFields;
    }
}
