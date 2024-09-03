package df.base.internal.spring.jpa.entity_graph.query;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import java.lang.reflect.Method;

public class QueryMethodFactory implements JpaQueryMethodFactory {

    private final QueryExtractor extractor;

    public QueryMethodFactory(QueryExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public JpaQueryMethod build(Method method, RepositoryMetadata metadata, ProjectionFactory factory) {
        return new QueryMethod(method, metadata, factory, extractor);
    }

}
