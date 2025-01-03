package df.common.extensions.persistence.entity_graph.invocation;

import df.common.proxy.MethodInvocation;
import df.common.proxy.MethodInvocationDecorator;

public class QueryMethodInvocation extends MethodInvocationDecorator {

    public QueryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
