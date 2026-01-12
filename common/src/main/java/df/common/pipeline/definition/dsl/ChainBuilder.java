package df.common.pipeline.definition.dsl;

import df.common.pipeline.definition.model.ChainDefinition;
import df.common.pipeline.definition.model.LinkDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ChainBuilder {

    private final String name;
    private String initial;
    private final Map<String, LinkBuilder> links = new LinkedHashMap<>();

    ChainBuilder(String name) {
        this.name = name;
    }

    public ChainBuilder initial(String linkName) {
        this.initial = linkName;
        return this;
    }

    public LinkBuilder link(String name) {
        return links.computeIfAbsent(name, LinkBuilder::new);
    }

    ChainDefinition build() {
        Map<String, LinkDefinition> built = new LinkedHashMap<>();
        for (LinkBuilder lb : links.values()) {
            LinkDefinition ld = lb.build();
            built.put(ld.name(), ld);
        }
        return new ChainDefinition(name, initial, built);
    }
}
