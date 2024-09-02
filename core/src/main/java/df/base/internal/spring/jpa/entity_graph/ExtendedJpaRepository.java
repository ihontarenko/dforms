package df.base.internal.spring.jpa.entity_graph;

import df.base.internal.spring.jpa.entity_graph.domain.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtendedJpaRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAll(EntityGraph graph);

}
