package df.base.internal.spring.jpa.entity_graph.invocation;

import df.base.internal.spring.jpa.entity_graph.MethodInvocationDecorator;
import org.aopalliance.intercept.MethodInvocation;

public class QueryMethodInvocation extends MethodInvocationDecorator {

    public QueryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

}
