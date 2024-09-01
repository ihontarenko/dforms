package df.base.internal.spring.data.jpa.entity.extention.query;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.Parameters;
import org.springframework.data.repository.query.ParametersSource;

import java.lang.reflect.Method;

public class EntityGraphJpaQueryMethod extends JpaQueryMethod {

    /**
     * Creates a {@link JpaQueryMethod}.
     *
     * @param method    must not be {@literal null}
     * @param metadata  must not be {@literal null}
     * @param factory   must not be {@literal null}
     * @param extractor must not be {@literal null}
     */
    protected EntityGraphJpaQueryMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
                                        QueryExtractor extractor) {
        super(method, metadata, factory, extractor);
    }

    @Override
    protected Parameters<?, ?> createParameters(ParametersSource source) {
        return new EntityGraphJpaParameters(source);
    }

}
