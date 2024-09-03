package df.base.internal.spring.jpa.entity_graph;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

public class EntityGraphJpaRepository<T, ID> extends SimpleJpaRepository<T, ID>
        implements ExtendedJpaRepository<T, ID> {

    public EntityGraphJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public EntityGraphJpaRepository(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    @Override
    public List<T> findAll(JpaEntityGraph graph) {
        return findAll();
    }

}
