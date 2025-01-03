package df.common.extensions.persistence.entity_graph.invocation;

import df.common.proxy.MethodInvocation;
import df.common.proxy.MethodInvocationDecorator;

public class EntityManagerMethodInvocation extends MethodInvocationDecorator {

    public EntityManagerMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
