package df.base.internal.spring.jpa.entity_graph;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class EntityGraphUtils {

    private static final Method JPA21_METHOD = createMethod();

    public static EntityGraph<?> createEntityGraph(EntityManager entityManager, Class<?> entityClass,
                                                   List<String> attributes) {
        EntityGraph<?> entityGraph = entityManager.createEntityGraph(entityClass);

        try {
            for (String attribute : attributes) {
                String[] components = StringUtils.delimitedListToStringArray(attribute, ".");
                requireNonNull(JPA21_METHOD).invoke(null, components, 0, entityGraph, null);
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

}
