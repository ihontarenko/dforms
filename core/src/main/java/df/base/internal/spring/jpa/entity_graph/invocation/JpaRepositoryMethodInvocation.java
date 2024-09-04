package df.base.internal.spring.jpa.entity_graph.invocation;

import df.base.internal.spring.jpa.entity_graph.MethodInvocationDecorator;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;

import java.lang.annotation.Annotation;

import static java.util.Objects.requireNonNull;

public class JpaRepositoryMethodInvocation extends MethodInvocationDecorator {

    public JpaRepositoryMethodInvocation(MethodInvocation delegate) {
        super(delegate);
    }

    public boolean isMethodAnnotated(Class<? extends Annotation> annotation) {
        return delegate.getMethod().isAnnotationPresent(annotation);
    }

}
