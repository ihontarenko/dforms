package df.base.common.spring.jpa.entity_graph.injector;

import df.base.common.spring.jpa.entity_graph.EntityGraphQueryHint;
import df.base.common.spring.jpa.entity_graph.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static df.base.common.spring.jpa.entity_graph.EntityGraphUtils.cleanupEntityGraphHints;

public class QueryParametersInjector implements Injector<EntityGraphQueryHint, Object[]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryParametersInjector.class);

    @Override
    public void inject(EntityGraphQueryHint source, Object[] destination) {
        int index = 0;

        for (Object argument : destination) {

            if (argument instanceof Map<?, ?>) {
                Map<String, Object> parameters = (Map<String, Object>) argument;
                cleanupEntityGraphHints(parameters);
                parameters.put(source.type().getKey(), source.entityGraph());
                destination[index] = parameters;
                LOGGER.debug("QUERY_PARAMETERS_INJECTOR: INJECT [{}]", source);
                break;
            }

            index++;
        }
    }

}
