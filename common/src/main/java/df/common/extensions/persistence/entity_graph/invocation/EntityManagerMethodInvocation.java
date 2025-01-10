package df.common.extensions.persistence.entity_graph.invocation;

import svit.proxy.MethodInvocation;
import svit.proxy.MethodInvocationDecorator;

public class EntityManagerMethodInvocation extends MethodInvocationDecorator {

    public EntityManagerMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
