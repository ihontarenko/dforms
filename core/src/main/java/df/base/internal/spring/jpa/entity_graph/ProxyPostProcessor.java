package df.base.internal.spring.jpa.entity_graph;

import jakarta.persistence.EntityManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Optional;

public class ProxyPostProcessor implements MethodInterceptor {

    private final EntityManager em;
    private final Class<?>      domainClass;

    public ProxyPostProcessor(EntityManager em, Class<?> domainClass) {
        this.em = em;
        this.domainClass = domainClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationDecorator decorator = new MethodInvocationDecorator(invocation);
        Optional<EntityGraph> graph = decorator.getTypedArgument(EntityGraph.class);

        if (invocation.getMethod().getName().startsWith("find") && graph.isPresent()) {
            System.out.println("POST PROCESSOR: " + invocation.getThis().getClass());
            System.out.println(graph.get());
        }

        return invocation.proceed();
    }

}
