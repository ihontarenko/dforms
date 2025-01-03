package df.common.extensions.persistence.entity_graph.injector;

import df.common.extensions.persistence.entity_graph.EntityGraphUtils;
import df.common.extensions.persistence.entity_graph.EntityGraphQueryHint;
import df.common.extensions.persistence.entity_graph.Injector;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryHintInjector implements Injector<EntityGraphQueryHint, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryHintInjector.class);

    @Override
    public void inject(EntityGraphQueryHint source, Object destination) {
        if (destination instanceof Query query) {
            EntityGraphUtils.cleanupEntityGraphHints(query.getHints());
            query.setHint(source.type().getKey(), source.entityGraph());
            LOGGER.debug("QUERY_HINT_INJECTOR: INJECT [{}]", source);
        }
    }

}
