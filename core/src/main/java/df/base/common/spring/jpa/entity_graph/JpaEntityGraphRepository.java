package df.base.common.spring.jpa.entity_graph;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEntityGraphRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAll(JpaEntityGraph graph);

}
