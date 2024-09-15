package df.base.common.pipeline;

import java.util.List;

public class PipelineProcessorChain implements PipelineChain {

    private final List<PipelineProcessor> processors;
    private       int                     currentProcessor = 0;

    public PipelineProcessorChain(List<PipelineProcessor> processors) {
        this.processors = processors;
    }

    public void proceed(PipelineContext context) throws Exception {
        if (currentProcessor < processors.size()) {
            PipelineProcessor current = processors.get(currentProcessor++);
            current.process(context, this);
        }
    }
}
