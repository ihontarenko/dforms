package df.base.common.extensions.persistence.entity_graph.invocation;

import df.base.common.proxy.MethodInvocation;
import df.base.common.proxy.MethodInvocationDecorator;

public class QueryMethodInvocation extends MethodInvocationDecorator {

    public QueryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
