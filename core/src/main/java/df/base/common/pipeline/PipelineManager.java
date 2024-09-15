package df.base.common.pipeline;

import df.base.common.pipeline.PipelineConfig.Link;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void loadProcessors(String config) {
        loadProcessors(createLoader(config).load(config));
    }

    public void loadProcessors(PipelineConfig config) {
        Map<String, PipelineProcessor> processorsInstances = new HashMap<>();

        for (PipelineConfig.Processor processorConfig : config.processors()) {
            try {
                Class<? extends PipelineProcessor> clazz     = (Class<? extends PipelineProcessor>) Class.forName(processorConfig.processor());
                PipelineProcessor                  processor = instantiate(clazz.getDeclaredConstructor());

                for (Map.Entry<String, String> parameter : processorConfig.parameters().entrySet()) {
                    setFieldValue(processor, parameter.getKey(), parameter.getValue());
                }

                processorsInstances.put(processorConfig.name(), processor);
            } catch (Exception e) {
                throw new PipelineException(e);
            }
        }

        Map<String, Link> mappedLinks = config.links().stream().collect(toMap(Link::name, identity()));
        Link              currentLink = config.links().get(0);

        processors.add(processorsInstances.get(currentLink.name()));

        while (currentLink != null) {
            processors.add(processorsInstances.get(currentLink.next()));
            currentLink = mappedLinks.get(currentLink.next());
        }
    }

    public void runPipeline(PipelineContext context) throws Exception {
        new PipelineProcessorChain(this.processors).proceed(context);
    }

}
