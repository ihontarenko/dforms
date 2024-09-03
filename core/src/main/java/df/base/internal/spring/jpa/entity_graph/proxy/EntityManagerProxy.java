package df.base.internal.spring.jpa.entity_graph.proxy;

import df.base.internal.spring.jpa.entity_graph.MethodInvocationDecorator;
import df.base.internal.spring.jpa.entity_graph.invocation.EntityManagerMethodInvocation;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Arrays.asList;

public class EntityManagerProxy implements MethodInterceptor {

    private static final String       FIND_METHOD          = "find";
    private static final List<String> CREATE_QUERY_METHODS = asList("createQuery", "createNamedQuery");
    private static final Logger       LOGGER               = LoggerFactory.getLogger(EntityManagerProxy.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationDecorator decorator  = new EntityManagerMethodInvocation(invocation);
        String                    methodName = decorator.getMethodName();

        if (FIND_METHOD.equals(methodName)) {
            LOGGER.debug("ENTITY_MANAGER_PROXY: Find method caught '{}' try to apply entity-graph", methodName);
            applyEntityGraph(null, decorator);
        }

        Object result = decorator.proceed();

        if (CREATE_QUERY_METHODS.contains(methodName)) {
            LOGGER.debug("ENTITY_MANAGER_PROXY: Create query method caught '{}'", methodName);
        }

        if (result instanceof Query query) {
            System.out.println(">>> QUERY: " + query);
        }

        return result;
    }

    private void applyEntityGraph(EntityGraph<?> entityGraph, MethodInvocationDecorator invocation) {
        System.out.println(invocation);
    }

}
