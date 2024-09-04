package df.base.internal.spring.jpa.entity_graph.proxy;

import df.base.internal.spring.jpa.entity_graph.EntityGraphQueryHint;
import df.base.internal.spring.jpa.entity_graph.ObjectsHolder;
import df.base.internal.spring.jpa.entity_graph.injector.QueryHintInjector;
import df.base.internal.spring.jpa.entity_graph.invocation.QueryMethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class QueryProxy implements MethodInterceptor {

    private static final Logger       LOGGER                = LoggerFactory.getLogger(QueryProxy.class);
    private static final String       UNWRAP_METHOD         = "unwrap";
    private static final List<String> EXECUTE_QUERY_METHODS = asList("getResultList", "getSingleResult", "getResultStream", "scroll");

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        QueryMethodInvocation decorator  = new QueryMethodInvocation(invocation);
        String                methodName = decorator.getMethodName();

        if (isUnwrapNull(decorator)) {
            /**
             * @link https://github.com/Cosium/spring-data-jpa-entity-graph/blob/master/core/src/main/java/com/cosium/spring/data/jpa/entity/graph/repository/support/RepositoryQueryEntityGraphInjector.java#L47
             * */
            LOGGER.warn("QUERY_PROXY: UNWRAP NULL DETECTED");
            return invocation.getThis();
        } else if (EXECUTE_QUERY_METHODS.contains(methodName)) {
            LOGGER.debug("QUERY_PROXY: EXECUTE_QUERY_METHOD [{}#{}]",
                    decorator.getMethodClassName(), decorator.getMethodName());
            Optional<EntityGraphQueryHint> optional = ObjectsHolder.getAndRemove(EntityGraphQueryHint.class);
            // inject entity graph
            optional.ifPresent(hint -> new QueryHintInjector().inject(hint, decorator.getThis()));
        }

        return decorator.proceed();
    }

    private boolean isUnwrapNull(QueryMethodInvocation invocation) {
        Object[] arguments  = invocation.getArguments();
        String   methodName = invocation.getMethodName();

        return UNWRAP_METHOD.equals(methodName) && arguments.length == 1 && arguments[0] == null;
    }

}
