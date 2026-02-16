package df.common.extensions.persistence.entity_graph;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public final class EntityGraphUtils {

    private static final Method JPA21_CREATE_GRAPH_METHOD = createMethod();

    public static EntityGraph<?> createEntityGraph(EntityManager entityManager, Class<?> entityClass,
                                                   List<String> attributes) {
        EntityGraph<?> entityGraph = entityManager.createEntityGraph(entityClass);

        try {
            for (String attribute : attributes) {
                String[] components = StringUtils.delimitedListToStringArray(attribute, ".");
                requireNonNull(JPA21_CREATE_GRAPH_METHOD).invoke(null, components, 0, entityGraph, null);
            }
        } catch (Throwable e) {
            throw new EntityGraphException("Unable to create EntityGraph with Jpa21Utils", e);
        }

        return entityGraph;
    }

    private static Method createMethod() {
        Method method;

        try {
            method = Jpa21Utils.class.getDeclaredMethod("createGraph",
                    String[].class, int.class, EntityGraph.class, Subgraph.class);
        } catch (NoSuchMethodException e) {
            throw new EntityGraphException(e);
        }

        method.setAccessible(true);

        return method;
    }

    public static void cleanupEntityGraphHints(Map<String, Object> hints) {
        if (hints != null) {
            hints.remove(EntityGraphType.FETCH.getKey());
            hints.remove(EntityGraphType.LOAD.getKey());
        }
    }

}
