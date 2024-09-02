package df.base.internal.spring.jpa.entity_graph;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.Parameters;
import org.springframework.data.repository.query.ParametersSource;

import java.lang.reflect.Method;

public class ExtendedJpaQueryMethod extends JpaQueryMethod {

    protected ExtendedJpaQueryMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
                                     QueryExtractor extractor) {
        super(method, metadata, factory, extractor);
    }

    @Override
    protected Parameters<?, ?> createParameters(ParametersSource source) {
        return new ExtendedJpaParameters(source);
    }

}
