package df.base.common.proxy;

import java.lang.reflect.Method;
import java.util.List;

import static df.base.common.libs.jbm.ReflectionUtils.invokeMethod;

public class MethodInvocationChain implements MethodInvocation {

    protected final List<MethodInterceptor> interceptors;
    protected final Object                  target;
    protected final Method                  method;
    protected final Object[]                arguments;
    protected final Object                  proxy;
    protected final ProxyConfig             proxyConfig;
    protected       int                     currentIndex = -1;

    public MethodInvocationChain(Object proxy, Object target, Method method, Object[] arguments,
                                 List<MethodInterceptor> interceptors, ProxyConfig proxyConfig) {
        this.interceptors = interceptors;
        this.proxyConfig = proxyConfig;
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        // shift and execute next interceptor in the chain
        if (interceptors.size() > ++currentIndex) {
            return interceptors.get(currentIndex).invoke(this);
        }

        // invoke real target method in the end of chain
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

    @Override
    public int getOrdinal() {
        return currentIndex;
    }

    @Override
    public ProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    @Override
    public Object getProxy() {
        return proxy;
    }
}
