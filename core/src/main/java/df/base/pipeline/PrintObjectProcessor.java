package df.base.pipeline;

import df.base.common.pipeline.PipelineChain;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineProcessor;

public class PrintObjectProcessor implements PipelineProcessor {

    private String requiredFields;

    @Override
    public Enum<?> process(PipelineContext context) throws Exception {
        System.out.println("print object");
        return null;
    }

    public String getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(String requiredFields) {
        this.requiredFields = requiredFields;
    }
}
