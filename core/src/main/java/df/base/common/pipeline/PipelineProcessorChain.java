package df.base.common.pipeline;

import df.base.pipeline.form.FormUpdateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static df.base.common.pipeline.ProcessorProperties.Configuration.MAX_PROCESSOR_CALLS;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public final class PipelineProcessorChain implements PipelineChain {

    private static final Logger                           LOGGER = LoggerFactory.getLogger(FormUpdateProcessor.class);
    private final        String                           initial;
    private final        Map<String, PipelineProcessor>   processors;
    private final        Map<String, ProcessorProperties> properties;

    public PipelineProcessorChain(String initial, Map<String, PipelineProcessor> processors, Map<String, ProcessorProperties> properties) {
        this.processors = processors;
        this.initial = initial;
        this.properties = properties;
    }

    public void proceed(PipelineContext context) throws Exception {
        String               processorName = initial;
        Map<String, Integer> countLimit    = new HashMap<>();

        LOGGER.info("[PIPELINE-CHAIN]: INITIAL PROCESS {}", initial);

        do {
            ProcessorProperties currentProperties = properties.get(processorName);
            PipelineProcessor   currentProcessor  = processors.get(processorName);
            int                 limit             = parseInt(ofNullable(
                    currentProperties.configuration(MAX_PROCESSOR_CALLS)).orElse("1"));

            countLimit.putIfAbsent(processorName, 0);

            if (limit <= countLimit.get(processorName)) {
                throw new RecursiveCallThresholdException("Processor '%s' has exceeded the maximum allowed threshold of '%d' recursive calls"
                    .formatted(currentProcessor.getClass().getSimpleName(), limit));
            }

            Enum<?> returnCode = currentProcessor.process(context);

            if (returnCode == null) {
                break;
            }

            countLimit.computeIfPresent(processorName, (key, count) -> ++count);

            processorName = currentProperties.getNext(returnCode.name());

            LOGGER.info("[PIPELINE-CHAIN]: RETURN CODE: '{}', NEXT PROCESSOR: '{}'", returnCode, processorName);
        } while (processorName != null);
    }


}
