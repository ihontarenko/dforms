package df.base.internal.spring.jpa.entity_graph.invocation;

import df.base.internal.spring.jpa.entity_graph.MethodInvocationDecorator;
import org.aopalliance.intercept.MethodInvocation;

public class EntityManagerMethodInvocation extends MethodInvocationDecorator {

    public EntityManagerMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
