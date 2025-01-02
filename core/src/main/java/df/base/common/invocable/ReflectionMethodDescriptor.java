package df.base.common.invocable;

import java.lang.reflect.Method;

import static df.base.common.container.StringUtils.underscored;

public class ReflectionMethodDescriptor implements MethodDescriptor {

    private final Method   method;
    private final Class<?> nativeClass;

    public ReflectionMethodDescriptor(Method method, Class<?> nativeClass) {
        this.method = method;
        this.nativeClass = nativeClass;
    }

    @Override
    public ClassTypeDescriptor getClassTypeDescriptor() {
        return new ReflectionClassTypeDescriptor(nativeClass);
    }

    @Override
    public Method getNativeMethod() {
        return method;
    }

    @Override
    public int getParametersCount() {
        return method.getParameterCount();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public String toString() {
        return "%s: [%s]".formatted(underscored(getClass().getSimpleName()).toUpperCase(), getNativeMethod());
    }

}
