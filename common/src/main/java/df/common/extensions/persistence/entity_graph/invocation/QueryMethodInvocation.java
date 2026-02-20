package df.common.extensions.persistence.entity_graph.invocation;

import org.jmouse.core.proxy.MethodInvocation;
import org.jmouse.core.proxy.MethodInvocationDecorator;

public class QueryMethodInvocation extends MethodInvocationDecorator {

    public QueryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
