package df.base.internal.spring.jpa.entity_graph.proxy;

import df.base.internal.spring.jpa.entity_graph.JpaEntityGraph;
import df.base.internal.spring.jpa.entity_graph.invocation.JpaRepositoryMethodInvocation;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JpaRepositoryProxy implements MethodInterceptor {

    private static final Logger        LOGGER = LoggerFactory.getLogger(JpaRepositoryProxy.class);
    private final        EntityManager entityManager;
    private final        Class<?>      entityClass;

    public JpaRepositoryProxy(EntityManager entityManager, Class<?> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        JpaRepositoryMethodInvocation decorator  = new JpaRepositoryMethodInvocation(invocation);
        String                        methodName = decorator.getMethodName();
        Optional<JpaEntityGraph>      argument   = decorator.getTypedArgument(JpaEntityGraph.class);

        if (argument.isPresent()) {
            JpaEntityGraph jpaEntityGraph = argument.get();

            LOGGER.debug("JPA_REPOSITORY_PROXY: A special argument was detected in '{}#{}'",
                    decorator.getRepository().getClass().getName(), methodName);

            EntityGraph<?> entityGraph = jpaEntityGraph.createEntityGraph(entityManager, entityClass);
        }

        return invocation.proceed();
    }

}
