package df.base.common.extensions.persistence.entity_graph.support;

import df.base.BasePackage;
import df.base.common.extensions.persistence.entity_graph.ExtendedJpaRepositoryFactory;
import df.base.common.extensions.persistence.entity_graph.proxy.EntityManagerProxy;
import df.base.common.proxy.AnnotationProxyFactory;
import df.base.common.proxy.ProxyFactory;
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
        ProxyFactory factory = new AnnotationProxyFactory(entityManager, BasePackage.class);
        factory.addInterceptor(new EntityManagerProxy());
        super.setEntityManager(factory.getProxy());
    }

}
