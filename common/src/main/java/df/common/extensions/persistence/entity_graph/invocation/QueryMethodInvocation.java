package df.common.extensions.persistence.entity_graph.invocation;

import svit.proxy.MethodInvocation;
import svit.proxy.MethodInvocationDecorator;

public class QueryMethodInvocation extends MethodInvocationDecorator {

    public QueryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
