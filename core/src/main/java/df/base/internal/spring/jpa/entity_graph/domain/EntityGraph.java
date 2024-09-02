package df.base.internal.spring.jpa.entity_graph.domain;

import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface EntityGraph {

    class Dynamic {



    }

    record Named(String name, EntityGraphType type) implements EntityGraph {

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

    }

}
