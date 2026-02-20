package df.common.extensions.persistence.entity_graph.proxy;

import df.common.extensions.persistence.entity_graph.ObjectsHolder;
import df.common.extensions.persistence.entity_graph.EntityGraphQueryHint;
import df.common.extensions.persistence.entity_graph.injector.QueryParametersInjector;
import df.common.extensions.persistence.entity_graph.invocation.EntityManagerMethodInvocation;
import jakarta.persistence.Query;
import org.jmouse.core.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jmouse.core.proxy.Intercept;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Intercept(EntityManagerProxy.class)
public class EntityManagerProxy implements MethodInterceptor {

    private static final String       FIND_METHOD          = "find";
    private static final List<String> CREATE_QUERY_METHODS = asList("createQuery", "createNamedQuery");
    private static final Logger       LOGGER               = LoggerFactory.getLogger(EntityManagerProxy.class);
    private static final ProxyFactory PROXY_FACTORY        = new DefaultProxyFactory(new QueryProxy());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationDecorator decorator  = new EntityManagerMethodInvocation(invocation);
        String                    methodName = decorator.getMethodName();

        if (FIND_METHOD.equals(methodName) || CREATE_QUERY_METHODS.contains(methodName)) {
            LOGGER.debug("ENTITY_MANAGER_PROXY: [FIND|CREATE] METHOD {}#{}",
                    decorator.getMethodClassName(), decorator.getMethodName());
        }

        // catch find method
        if (FIND_METHOD.equals(methodName)) {
            Optional<EntityGraphQueryHint> optional = ObjectsHolder.getAndRemove(EntityGraphQueryHint.class);
            // inject entity graph
            optional.ifPresent(hint -> new QueryParametersInjector().inject(hint, decorator.getArguments()));
        }

        Object result = decorator.proceed();

        // catch create methods ["createQuery", "createNamedQuery"]
        if (CREATE_QUERY_METHODS.contains(methodName)
                && result instanceof Query query
                && ObjectsHolder.exists(EntityGraphQueryHint.class)) {
            // if applicable method name and result is Query and EntityGraphQueryHint is present then we proxy it
            result = PROXY_FACTORY.createProxy(query);
        }

        return result;
    }

}
