package df.base.common.processing;

public interface Interceptor<C extends ProcessingContext<T>, T> {
    void intercept(T target, C context);
}
