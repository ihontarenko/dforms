package df.base.common.proxy;

import df.base.common.proxy.cglib.SimpleNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class CglibProxy implements Proxy, MethodInterceptor {

    private final ProxyConfig proxyConfig;

    public CglibProxy(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public <T> T getProxy(ClassLoader classLoader) {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(proxyConfig.getTargetClass());
        enhancer.setNamingPolicy(new SimpleNamingPolicy());
        enhancer.setCallback(this);
        enhancer.setInterceptDuringConstruction(false);

        return (T) enhancer.create();
    }

    @Override
    public ProxyEngine getProxyEngine() {
        return ProxyEngine.CGLIB;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] arguments, MethodProxy proxy) throws Throwable {
        Object   returnValue;
        Class<?> returnClass = method.getReturnType();
        Object   target      = proxyConfig.getTarget();

        try {
            List<Interceptor> interceptors = proxyConfig.getInterceptors();
            MethodInvocation  invocation   = new MethodInvocationChain(proxy, target, method, arguments, interceptors);

            returnValue = invocation.proceed();
        } catch (Throwable throwable) {
            throw new ProxyInvocationException(throwable);
        }

        if (returnValue != null && returnValue == target && returnClass != Object.class && returnClass.isInstance(
                proxy)) {
            returnValue = proxy;
        } else if (returnValue == null && returnClass.isPrimitive() && returnClass != void.class) {
            throw new ProxyInvocationException(
                    "Method '%s' returned null, but a primitive '%s' value was expected."
                            .formatted(method, returnClass));
        }

        return returnValue;
    }

}