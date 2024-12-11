package df.base.common.extensions.persistence.entity_graph.support;

import df.base.common.extensions.persistence.entity_graph.JpaEntityGraph;
import df.base.common.extensions.persistence.entity_graph.JpaEntityGraphRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;
import java.util.Optional;

public class SimpleJpaEntityGraphRepository<T, ID> extends SimpleJpaRepository<T, ID>
        implements JpaEntityGraphRepository<T, ID> {

    public SimpleJpaEntityGraphRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public SimpleJpaEntityGraphRepository(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    @Override
    public Optional<T> findById(ID id, JpaEntityGraph entityGraph) {
        return findById(id);
    }

    @Override
    public List<T> findAll(JpaEntityGraph graph) {
        return findAll();
    }

}
