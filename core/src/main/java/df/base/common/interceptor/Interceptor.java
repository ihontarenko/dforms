package df.base.common.interceptor;

public interface Interceptor<C extends ProcessingContext, T> {
    void intercept(T target, C context);
}
