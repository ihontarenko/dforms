package df.base.common.interceptor;

public interface Interceptor<T> {
    void intercept(T target, ChainExecutor chain);
}
