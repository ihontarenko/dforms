package df.common.extensions.persistence.entity_graph;

import df.common.extensions.persistence.entity_graph.proxy.JpaRepositoryProxy;
import df.common.extensions.persistence.entity_graph.query.QueryMethodFactory;
import df.common.extensions.persistence.entity_graph.support.SimpleJpaEntityGraphRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

@SuppressWarnings({"all"})
public class ExtendedJpaRepositoryFactory extends JpaRepositoryFactory {

    public ExtendedJpaRepositoryFactory(EntityManager em) {
        super(em);
        addRepositoryProxyPostProcessor((factory, ri)
                -> factory.addAdvice(new JpaRepositoryProxy(em, ri.getDomainType())));
        setQueryMethodFactory(new QueryMethodFactory(PersistenceProvider.fromEntityManager(em)));
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleJpaEntityGraphRepository.class;
    }

}
