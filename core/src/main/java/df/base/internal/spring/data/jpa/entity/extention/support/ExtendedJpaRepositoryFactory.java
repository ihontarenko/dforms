package df.base.internal.spring.data.jpa.entity.extention.support;

import df.base.internal.spring.data.jpa.entity.extention.query.EntityGraphJpaQueryMethodFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

@SuppressWarnings({"all"})
public class ExtendedJpaRepositoryFactory extends JpaRepositoryFactory {
    public ExtendedJpaRepositoryFactory(EntityManager em) {
        super(em);
        addRepositoryProxyPostProcessor((factory, ri)
                -> factory.addAdvice(new EntityGraphPostProcessor(em, ri.getDomainType())));
        setQueryMethodFactory(
                new EntityGraphJpaQueryMethodFactory(
                        PersistenceProvider.fromEntityManager(em)
                )
        );
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return ExtendedSimpleJpaRepository.class;
    }

}
