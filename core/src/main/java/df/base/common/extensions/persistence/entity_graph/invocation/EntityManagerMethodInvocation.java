package df.base.common.extensions.persistence.entity_graph.invocation;

import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.MethodInvocationDecorator;

public class EntityManagerMethodInvocation extends MethodInvocationDecorator {

    public EntityManagerMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
