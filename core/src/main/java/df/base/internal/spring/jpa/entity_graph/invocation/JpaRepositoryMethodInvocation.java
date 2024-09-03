package df.base.internal.spring.jpa.entity_graph.invocation;

import df.base.internal.spring.jpa.entity_graph.MethodInvocationDecorator;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;

import static java.util.Objects.requireNonNull;

public class JpaRepositoryMethodInvocation extends MethodInvocationDecorator {

    public JpaRepositoryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

    public Object getRepository() {
        if (delegate instanceof ProxyMethodInvocation) {
            return ((ProxyMethodInvocation) delegate).getProxy();
        } else {
            return delegate.getThis();
        }
    }

}
