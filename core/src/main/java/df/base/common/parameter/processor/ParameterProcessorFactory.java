package df.base.common.parameter.processor;

import java.util.HashMap;
import java.util.Map;

public class ParameterProcessorFactory {

    private final Map<Class<?>, ParameterProcessor> processors = new HashMap<>();

    public ParameterProcessor loadProcessor(Class<?> processorClass) {
        if (processorClass != null && processors.containsKey(processorClass)) {
            return processors.get(processorClass);
        }

        throw new ParameterProcessorNotFoundException("NO PROCESSOR FOR: [%s]".formatted(processorClass));
    }

}
