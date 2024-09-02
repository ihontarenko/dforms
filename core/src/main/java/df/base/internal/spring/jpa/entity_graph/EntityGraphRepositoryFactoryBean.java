package df.base.internal.spring.jpa.entity_graph;

import jakarta.persistence.EntityManager;
import org.springframework.aop.framework.ProxyFactory;
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
    public void setEntityManager(EntityManager em) {
        ProxyFactory factory = new ProxyFactory(em);

        factory.addAdvice(new EntityManagerProxy());

        super.setEntityManager((EntityManager) factory.getProxy());
    }

}
