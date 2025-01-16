package df.common.extensions.persistence.entity_graph.support;

import df.common.extensions.persistence.entity_graph.ExtendedJpaRepositoryFactory;
import df.common.extensions.persistence.entity_graph.proxy.EntityManagerProxy;
import svit.proxy.DefaultProxyFactory;
import svit.proxy.ProxyFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class EntityGraphRepositoryFactoryBean<R extends Repository<T, I>, T, I>
        extends JpaRepositoryFactoryBean<R, T, I> {

    private static final Logger       LOGGER        = LoggerFactory.getLogger(EntityGraphRepositoryFactoryBean.class);
    private static final ProxyFactory PROXY_FACTORY = new DefaultProxyFactory(new EntityManagerProxy());

    public EntityGraphRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExtendedJpaRepositoryFactory(entityManager);
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        EntityManager entityManagerProxy = PROXY_FACTORY.createProxy(entityManager);

        LOGGER.info("CREATE PROXY FOR ENTITY_MANAGER: {}", entityManagerProxy);

        super.setEntityManager(entityManagerProxy);
    }

}
