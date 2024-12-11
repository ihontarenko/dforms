package df.base.common.extensions.persistence.entity_graph;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaEntityGraphRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAll(JpaEntityGraph graph);

    Optional<T> findById(ID id, JpaEntityGraph entityGraph);

}
