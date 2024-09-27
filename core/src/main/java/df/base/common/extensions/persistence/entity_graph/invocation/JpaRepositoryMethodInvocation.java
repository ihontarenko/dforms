package df.base.common.extensions.persistence.entity_graph.invocation;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Arrays.stream;

public class JpaRepositoryMethodInvocation implements MethodInvocation {

    private final MethodInvocation delegate;

    public JpaRepositoryMethodInvocation(MethodInvocation delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Optional<T> getTypedArgument(Class<T> type) {
        return (Optional<T>) stream(delegate.getArguments())
                .filter(argument -> type.isAssignableFrom(argument.getClass())).findFirst();
    }

    public boolean isMethodAnnotated(Class<? extends Annotation> annotation) {
        return delegate.getMethod().isAnnotationPresent(annotation);
    }

    public String getMethodName() {
        return getMethod().getName();
    }

    public Class<?> getMethodClass() {
        return getMethod().getDeclaringClass();
    }

    public String getMethodClassName() {
        return getMethodClass().getName();
    }

    @Override
    public Method getMethod() {
        return delegate.getMethod();
    }

    @Override
    public Object[] getArguments() {
        return delegate.getArguments();
    }

    @Override
    public Object proceed() throws Throwable {
        return delegate.proceed();
    }

    @Override
    public Object getThis() {
        return delegate.getThis();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return delegate.getStaticPart();
    }
}
