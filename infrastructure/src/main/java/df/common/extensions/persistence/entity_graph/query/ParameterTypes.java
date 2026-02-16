package df.common.extensions.persistence.entity_graph.query;

import df.common.extensions.persistence.entity_graph.JpaEntityGraph;

public enum ParameterTypes {

    ENTITY_GRAPH(JpaEntityGraph.class);

    private final Class<?> type;

    ParameterTypes(Class<?> type) {
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

}
