package df.base.common.pipeline;

import df.base.common.pipeline.PipelineConfig.Link;

import java.util.*;

import static df.base.common.libs.jbm.ReflectionUtils.instantiate;
import static df.base.common.libs.jbm.ReflectionUtils.setFieldValue;
import static df.base.common.pipeline.ConfigLoader.createLoader;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class PipelineManager {

    private final List<PipelineProcessor> processors = new ArrayList<>();

    public PipelineManager(String configPath) {
        loadProcessors(configPath);
    }

    public PipelineChain loadProcessors(String config) {
        loadProcessors(createLoader(config).load(config));
    }

    public void loadProcessors(PipelineConfig config) {
        Map<String, PipelineProcessor> processors = new HashMap<>();

        for (PipelineConfig.Processor processorConfig : config.processors()) {
            try {
                Class<? extends PipelineProcessor> clazz     = (Class<? extends PipelineProcessor>) Class.forName(processorConfig.processor());
                PipelineProcessor                  processor = instantiate(clazz.getDeclaredConstructor());

                for (Map.Entry<String, String> parameter : processorConfig.parameters().entrySet()) {
                    setFieldValue(processor, parameter.getKey(), parameter.getValue());
                }

                processors.put(processorConfig.name(), processor);
            } catch (Exception e) {
                throw new PipelineException(e);
            }
        }

        var links = config.links().stream().collect(toMap(Link::name, identity()));



    }

    public void linkProcessors() {

    }

}
