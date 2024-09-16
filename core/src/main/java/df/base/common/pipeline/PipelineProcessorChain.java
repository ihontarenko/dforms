package df.base.common.pipeline;

import java.util.Map;

public record PipelineProcessorChain(Map<String, PipelineProcessor> processors,
        Transitions transitions) implements PipelineChain {

    public void proceed(PipelineContext context) throws Exception {
        String processorName = transitions.getInitial();

        do {
            PipelineProcessor current    = processors.get(processorName);
            Enum<?>           returnCode = current.process(context);

            if (returnCode == null) {
                break;
            }

            processorName = transitions.getNextFor(returnCode.name());
        } while (processorName != null);
    }

}
