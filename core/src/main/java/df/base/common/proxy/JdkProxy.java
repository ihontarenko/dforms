package df.base.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import static df.base.common.libs.jbm.ReflectionUtils.isEqualsMethod;
import static df.base.common.libs.jbm.ReflectionUtils.isHashCodeMethod;
import static java.lang.reflect.Proxy.newProxyInstance;

public class JdkProxy implements InvocationHandler, Proxy {

    private final ProxyConfig proxyConfig;

    public JdkProxy(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        Object   returnValue;
        Class<?> returnClass = method.getReturnType();
        Object   target      = proxyConfig.getTarget();

        if (isEqualsMethod(method) && !proxyConfig.hasEquals()) {

        } else if (isHashCodeMethod(method) && !proxyConfig.hasHashCode()) {

        }

        try {
            List<MethodInterceptor> interceptors = proxyConfig.getInterceptors();
            MethodInvocation        invocation   = new MethodInvocationChain(
                    proxy, target, method, arguments, interceptors, proxyConfig);

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

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return newProxyInstance(classLoader, proxyConfig.getInterfaces().toArray(Class[]::new), this);
    }

    @Override
    public ProxyEngine getProxyEngine() {
        return ProxyEngine.JDK;
    }

}
