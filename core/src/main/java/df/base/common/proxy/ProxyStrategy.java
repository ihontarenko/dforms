package df.base.common.proxy;

@FunctionalInterface
public interface ProxyStrategy {
    <T> T createProxy(Class<T> targetClass, Object target, Interceptor interceptor);
}
