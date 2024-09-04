package df.base.common.extensions.persistence.entity_graph;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Arrays.stream;

abstract public class MethodInvocationDecorator implements MethodInvocation {

    protected final MethodInvocation delegate;

    public MethodInvocationDecorator(MethodInvocation delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Optional<T> getTypedArgument(Class<T> type) {
        return (Optional<T>) stream(delegate.getArguments())
                .filter(argument -> type.isAssignableFrom(argument.getClass())).findFirst();
    }

    @Override
    public Method getMethod() {
        return delegate.getMethod();
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

    public Class<?> getThisClass() {
        return getThis().getClass();
    }

    public String getThisClassName() {
        return getThisClass().getSimpleName();
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
