package df.common.extensions.persistence.entity_graph.invocation;

import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.MethodInvocationDecorator;

public class EntityManagerMethodInvocation extends MethodInvocationDecorator {

    public EntityManagerMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
