package df.common.extensions.persistence.entity_graph.support;

import df.common.extensions.persistence.entity_graph.ExtendedJpaRepositoryFactory;
import df.common.extensions.persistence.entity_graph.proxy.EntityManagerProxy;
import df.common.proxy.AnnotationProxyFactory;
import df.common.proxy.ProxyFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class EntityGraphRepositoryFactoryBean<R extends Repository<T, I>, T, I>
        extends JpaRepositoryFactoryBean<R, T, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityGraphRepositoryFactoryBean.class);

    public EntityGraphRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExtendedJpaRepositoryFactory(entityManager);
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        ProxyFactory factory = new AnnotationProxyFactory(entityManager);
        factory.addInterceptor(new EntityManagerProxy());
        EntityManager entityManagerProxy = factory.getProxy();

        LOGGER.info("CREATE PROXY FOR ENTITY_MANAGER: {}", entityManagerProxy);

        super.setEntityManager(entityManagerProxy);
    }

}
