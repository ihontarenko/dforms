package df.base.internal.spring.jpa.entity_graph;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;

import java.lang.reflect.Method;

import static java.util.Objects.requireNonNull;

public final class EntityGraphUtils {

    private static Method JPA21_METHOD;

    private EntityGraphUtils() {
        JPA21_METHOD = createMethod();
    }

    public static EntityGraph<?> createEntityGraph(EntityManager entityManager, Class<?> domainClass,
                                                   JpaEntityGraph jpaEntityGraph) {
        EntityGraph<?> entityGraph = entityManager.createEntityGraph(domainClass);

        try {
            requireNonNull(JPA21_METHOD).invoke(null, jpaEntityGraph, entityGraph);
        } catch (Throwable e) {
            throw new EntityGraphException("Unable to create EntityGraph with Jpa21Utils", e);
        }

        return entityGraph;
    }

    private static Method createMethod() {
        Method method = null;

        try {
            method = Jpa21Utils.class.getDeclaredMethod(
                    "configureFetchGraphFrom", JpaEntityGraph.class, EntityGraph.class);
        } catch (NoSuchMethodException ignore) { }

        if (method != null) {
            method.setAccessible(true);
        }

        return method;
    }

}
