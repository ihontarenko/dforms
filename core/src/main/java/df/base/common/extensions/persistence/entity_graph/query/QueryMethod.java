package df.base.common.extensions.persistence.entity_graph.query;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.ParametersSource;

import java.lang.reflect.Method;

public class QueryMethod extends JpaQueryMethod {

    protected QueryMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
                          QueryExtractor extractor) {
        super(method, metadata, factory, extractor);
    }

    @Override
    protected org.springframework.data.repository.query.Parameters<?, ?> createParameters(ParametersSource source) {
        return new Parameters(source);
    }

}
