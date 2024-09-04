package df.base.common.spring.jpa.entity_graph.injector;

import df.base.common.spring.jpa.entity_graph.EntityGraphQueryHint;
import df.base.common.spring.jpa.entity_graph.Injector;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static df.base.common.spring.jpa.entity_graph.EntityGraphUtils.cleanupEntityGraphHints;

public class QueryHintInjector implements Injector<EntityGraphQueryHint, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryHintInjector.class);

    @Override
    public void inject(EntityGraphQueryHint source, Object destination) {
        if (destination instanceof Query query) {
            cleanupEntityGraphHints(query.getHints());
            query.setHint(source.type().getKey(), source.entityGraph());
            LOGGER.debug("QUERY_HINT_INJECTOR: INJECT [{}]", source);
        }
    }

}
