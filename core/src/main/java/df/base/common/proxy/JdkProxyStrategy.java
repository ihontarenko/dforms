package df.base.common.proxy;

import df.base.common.proxy.MethodInvocation.Executable;

import java.lang.reflect.Proxy;

public class JdkProxyStrategy implements ProxyStrategy {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProxy(Class<T> targetClass, Object target, Interceptor interceptor) {
        return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), target.getClass().getInterfaces(),
                (proxy, method, arguments) -> {
                    Executable       executable = (t, m, a) -> m.invoke(t, a);
                    MethodInvocation invocation = new MethodInvocation.DefaultMethodInvocation(
                            target, method, arguments, executable);

                    return interceptor.invoke(invocation);
                }
        );
    }
}