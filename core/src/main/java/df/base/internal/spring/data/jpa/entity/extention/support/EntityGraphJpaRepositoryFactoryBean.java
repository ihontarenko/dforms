package df.base.internal.spring.data.jpa.entity.extention.support;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import static df.base.internal.spring.data.jpa.entity.extention.support.RepositoryEntityManagerInjector.proxy;

public class EntityGraphJpaRepositoryFactoryBean<R extends Repository<T, I>, T, I>
        extends JpaRepositoryFactoryBean<R, T, I> {

    public EntityGraphJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExtendedJpaRepositoryFactory(entityManager);
    }

    @Override
    public void setEntityManager(EntityManager em) {
        super.setEntityManager(proxy(em));
    }

}
