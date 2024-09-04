package df.base.common.spring.jpa.entity_graph.query;

import df.base.common.spring.jpa.entity_graph.JpaEntityGraph;

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
