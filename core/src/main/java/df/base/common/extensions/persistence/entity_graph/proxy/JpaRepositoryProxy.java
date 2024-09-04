package df.base.common.extensions.persistence.entity_graph.proxy;

import df.base.common.extensions.persistence.entity_graph.EntityGraphException;
import df.base.common.extensions.persistence.entity_graph.ObjectsHolder;
import df.base.common.extensions.persistence.entity_graph.invocation.JpaRepositoryMethodInvocation;
import df.base.common.extensions.persistence.entity_graph.EntityGraphQueryHint;
import df.base.common.extensions.persistence.entity_graph.JpaEntityGraph;
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

            if (decorator.isMethodAnnotated(org.springframework.data.jpa.repository.EntityGraph.class)) {
                throw new EntityGraphException("@EntityGraph already present on method %s#%s"
                        .formatted(decorator.getMethodClassName(), decorator.getMethodName()));
            }

            LOGGER.debug("JPA_REPOSITORY_PROXY: JpaEntityGraph found '{}({})#{}'",
                    decorator.getMethodClassName(), entityClass.getSimpleName(), methodName);

            EntityGraph<?> entityGraph = jpaEntityGraph.createEntityGraph(entityManager, entityClass);

            ObjectsHolder.set(new EntityGraphQueryHint(jpaEntityGraph.entityGraphType(), entityGraph));
        }

        return invocation.proceed();
    }

}
