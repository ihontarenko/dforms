package df.base.internal.spring.jpa.entity_graph;

public enum ExtendedParameterTypes {

    ENTITY_GRAPH(JpaEntityGraph.class);

    private final Class<?> type;

    ExtendedParameterTypes(Class<?> type) {
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

}
