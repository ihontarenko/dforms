package df.base.common.proxy;

import java.lang.reflect.Method;
import java.util.List;

import static df.base.common.libs.jbm.ReflectionUtils.invokeMethod;

public class MethodInvocationChain implements MethodInvocation {

    protected final List<Interceptor> interceptors;
    protected final Object            target;
    protected final Method            method;
    protected final Object[]          arguments;
    protected final Object            proxy;
    protected       int               currentIndex = -1;

    public MethodInvocationChain(Object proxy, Object target, Method method, Object[] arguments, List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        if (interceptors.size() > ++currentIndex) {
            return interceptors.get(currentIndex).invoke(this);
        }

        return invokeMethod(target, method, arguments);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object getTarget() {
        return target;
    }

}
