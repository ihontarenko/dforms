package df.base.internal.spring.jpa.entity_graph;

import df.base.internal.spring.jpa.entity_graph.domain.EntityGraph;
import jakarta.persistence.EntityManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;

import java.util.Optional;

public class EntityGraphRepositoryProxy implements MethodInterceptor {

    private final EntityManager em;
    private final Class<?>      domainClass;

    public EntityGraphRepositoryProxy(EntityManager em, Class<?> domainClass) {
        this.em = em;
        this.domainClass = domainClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationDecorator decorator = new MethodInvocationDecorator(invocation);
        Optional<EntityGraph>     graph     = decorator.getTypedArgument(EntityGraph.class);

        if (graph.isPresent()) {
            switch (graph.get()) {
                case EntityGraph.Dynamic deg -> {}
                case EntityGraph.Named neg -> {}
            }

           /* JpaEntityGraph jpaEntityGraph = new JpaEntityGraph(
                    entityGraph.name(), entityGraph.type(), entityGraph.attributes());

            EntityGraphUtils.createEntityGraph(em, domainClass, jpaEntityGraph);*/
        }

        return invocation.proceed();
    }

}
