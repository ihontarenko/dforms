package df.base.common.spring.jpa.entity_graph.support;

import df.base.common.spring.jpa.entity_graph.ExtendedJpaRepositoryFactory;
import df.base.common.spring.jpa.entity_graph.ProxyUtils;
import df.base.common.spring.jpa.entity_graph.proxy.EntityManagerProxy;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;


public class EntityGraphRepositoryFactoryBean<R extends Repository<T, I>, T, I>
        extends JpaRepositoryFactoryBean<R, T, I> {

    public EntityGraphRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExtendedJpaRepositoryFactory(entityManager);
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        // proxy EntityManager's method
        super.setEntityManager(ProxyUtils.proxy(entityManager, new EntityManagerProxy()));
    }

}