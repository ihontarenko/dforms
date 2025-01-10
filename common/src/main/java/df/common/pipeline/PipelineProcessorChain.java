package df.common.pipeline;

import svit.context.ResultContext;
import df.common.pipeline.context.PipelineContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public record PipelineProcessorChain(String initial,
                                     Map<String, PipelineProcessor> processors,
                                     Map<String, ProcessorProperties> properties) implements PipelineChain {

    private static final Logger            LOGGER             = getLogger(PipelineProcessorChain.class);
    private static final PipelineProcessor FALLBACK_PROCESSOR = new DefaultFallbackProcessor();

    public void proceed(PipelineContext context) throws Exception {
        String              processorName = initial;
        Map<String, Object> visitor       = new HashMap<>();

        LOGGER.info("[PIPELINE-CHAIN]: INITIAL PROCESS {}", initial);

        do {
            Enum<?>             returnCode;
            ProcessorProperties currentProperties = properties.get(processorName);
            PipelineProcessor   currentProcessor  = processors.get(processorName);

            if (currentProcessor == null) {
                throw new MissingProcessorLinkException(
                        "No processor with the link name '%s' was located. Check the pipeline file for correct linking."
                                .formatted(processorName));
            }

            if (visitor.containsValue(processorName)) {
                throw new CyclicInvocationDetected(
                        "Processor '%s' encountered a recursive call".formatted(processorName));
            }

            try {
                returnCode = currentProcessor.process(context);
            } catch (Exception exception) {
                handleFallback(context, currentProperties, exception);
                break;
            }

            if (context.isStopped()) {
                LOGGER.info("[PIPELINE-CHAIN]: PROCESSOR '{}' STOPPED THE PROCESSOR-CHAIN", processorName);
                break;
            }

            if (returnCode == null) {
                throw new InvalidProcessorReturnException(
                        "Processor '%s' must return a valid code; null is unacceptable".formatted(processorName));
            }

            visitor.put(processorName, true);

            processorName = currentProperties.getNext(returnCode.name());

            LOGGER.info("[PIPELINE-CHAIN]: RETURN: '{}' -> NEXT: '{}'", returnCode, processorName);

        } while (processorName != null);
    }

    private void handleFallback(PipelineContext context, ProcessorProperties properties, Exception exception)
            throws Exception {
        PipelineProcessor fallback = FALLBACK_PROCESSOR;
        ResultContext     result   = context.getResultContext();

        if (properties != null && properties.fallback() != null) {
            fallback = processors.get(properties.fallback());
        }

        context.setProperty("EXCEPTION", exception);
        result.addError("EXCEPTION", exception.getMessage());

        fallback.process(context);

        context.setProperty(Throwable.class, exception);

        LOGGER.error("[PIPELINE-CHAIN]: ERROR OCCURRED: '{}', FALLBACK PROCESSOR: '{}'",
                exception.getMessage(), fallback.getClass().getName());
    }

}
