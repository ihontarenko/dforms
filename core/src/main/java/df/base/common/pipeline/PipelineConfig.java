package df.base.common.pipeline;

import java.util.List;
import java.util.Map;

public record PipelineConfig(List<Processor> processors, List<Link> links) {

    public record Processor(String name, String processor, Map<String, String> parameters) {

    }

    public record Link(String name, String next) {

    }

}
