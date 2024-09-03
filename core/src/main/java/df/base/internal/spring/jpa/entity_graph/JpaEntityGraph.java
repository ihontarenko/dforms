package df.base.internal.spring.jpa.entity_graph;

import df.base.internal.spring.jpa.entity_graph.proxy.JpaRepositoryProxy;
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

    record Dynamic(EntityGraphType type,
                   List<String> attributes) implements JpaEntityGraph {

        public static Builder load() {
            return builder(EntityGraphType.LOAD);
        }


        public static Dynamic load(String... attributes) {
            return load().attribute(attributes).build();
        }

        public static Builder fetch() {
            return builder(EntityGraphType.FETCH);
        }


        public static Dynamic fetch(String... attributes) {
            return fetch().attribute(attributes).build();
        }

        public static Builder builder(EntityGraphType type) {
            return new Builder(type);
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

            private Builder(EntityGraphType type) {
                this.type = requireNonNull(type);
            }

            public Builder attribute(String... paths) {
                attributes.addAll(List.of(paths));
                return this;
            }

            public Dynamic build() {
                return new Dynamic(type, attributes);
            }

        }

        @Override
        public String toString() {
            return "Dynamic[type=%s, attributes=%s]".formatted(type, attributes);
        }
    }

    record Named(String name, EntityGraphType type) implements JpaEntityGraph {

        public Named(String name, EntityGraphType type) {
            this.name = name;
            this.type = type;
        }

        public static Named fetch(String name) {
            return new Named(name, EntityGraphType.FETCH);
        }

        public static Named load(String name) {
            return new Named(name, EntityGraphType.LOAD);
        }

        public static Named name(String name, EntityGraphType type) {
            return new Named(name, type);
        }

        public static Named name(String name) {
            return new Named(name, EntityGraphType.FETCH);
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
