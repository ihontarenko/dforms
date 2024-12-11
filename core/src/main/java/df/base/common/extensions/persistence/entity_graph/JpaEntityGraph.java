package df.base.common.extensions.persistence.entity_graph;

import df.base.common.extensions.persistence.entity_graph.proxy.JpaRepositoryProxy;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public interface JpaEntityGraph {

    Logger LOGGER = LoggerFactory.getLogger(JpaRepositoryProxy.class);

    EntityGraph<?> createEntityGraph(EntityManager entityManager, Class<?> entityClass);

    EntityGraphType entityGraphType();

    record DynamicEntityGraph(EntityGraphType type, List<String> attributes,
                              Class<?> entity) implements JpaEntityGraph {

        public static Builder load() {
            return builder(EntityGraphType.LOAD);
        }

        public static DynamicEntityGraph load(String... attributes) {
            return load().attribute(attributes).build();
        }

        public static Builder load(Class<?> entity) {
            return builder(entity, EntityGraphType.LOAD);
        }

        public static DynamicEntityGraph load(Class<?> entity, String... attributes) {
            return builder(entity, EntityGraphType.LOAD).attribute(attributes).build();
        }

        public static Builder fetch() {
            return builder(EntityGraphType.FETCH);
        }

        public static Builder fetch(Class<?> entity) {
            return builder(entity, EntityGraphType.FETCH);
        }

        public static DynamicEntityGraph fetch(Class<?> entity, String... attributes) {
            return builder(entity, EntityGraphType.FETCH).attribute(attributes).build();
        }

        public static DynamicEntityGraph fetch(String... attributes) {
            return fetch().attribute(attributes).build();
        }

        public static Builder builder(EntityGraphType type) {
            return new Builder(type);
        }

        public static Builder builder(Class<?> entity, EntityGraphType type) {
            return new Builder(entity, type);
        }

        @Override
        public EntityGraphType entityGraphType() {
            return type();
        }

        @Override
        public EntityGraph<?> createEntityGraph(EntityManager entityManager, Class<?> entityClass) {
            List<String> attributes = new ArrayList<>(this.attributes);

            Collections.sort(attributes);

            LOGGER.debug("JPA_ENTITY_GRAPH: Create '{}'", this);

            return EntityGraphUtils.createEntityGraph(entityManager, entityClass, attributes);
        }

        public static class Builder {

            private final EntityGraphType type;
            private final List<String>    attributes = new ArrayList<>();
            private       Class<?>        entity;

            private Builder(Class<?> entity, EntityGraphType type) {
                this.entity = entity;
                this.type = requireNonNull(type);
            }

            private Builder(EntityGraphType type) {
                this(null, type);
            }

            public Builder attribute(String... paths) {
                attributes.addAll(List.of(paths));
                return this;
            }

            public Builder entity(Class<?> entity) {
                this.entity = entity;
                return this;
            }

            public DynamicEntityGraph build() {
                return new DynamicEntityGraph(type, attributes, entity);
            }

        }
    }

    record NamedEntityGraph(String name, EntityGraphType type) implements JpaEntityGraph {

        public NamedEntityGraph(String name, EntityGraphType type) {
            this.name = name;
            this.type = type;
        }

        public static NamedEntityGraph fetch(String name) {
            return new NamedEntityGraph(name, EntityGraphType.FETCH);
        }

        public static NamedEntityGraph load(String name) {
            return new NamedEntityGraph(name, EntityGraphType.LOAD);
        }

        public static NamedEntityGraph name(String name, EntityGraphType type) {
            return new NamedEntityGraph(name, type);
        }

        public static NamedEntityGraph name(String name) {
            return new NamedEntityGraph(name, EntityGraphType.FETCH);
        }

        @Override
        public EntityGraphType entityGraphType() {
            return type;
        }

        @Override
        public EntityGraph<?> createEntityGraph(EntityManager entityManager, Class<?> entityClass) {
            LOGGER.debug("JPA_ENTITY_GRAPH: Get '{}'", this);

            return entityManager.getEntityGraph(name);
        }
    }

}
