package df.base.internal.spring.jpa.entity_graph;

import df.base.internal.spring.jpa.entity_graph.domain.EntityGraph;

public enum ExtendedParameterTypes {

    ENTITY_GRAPH(EntityGraph.class);

    private final Class<?> type;

    ExtendedParameterTypes(Class<?> type) {
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

}
