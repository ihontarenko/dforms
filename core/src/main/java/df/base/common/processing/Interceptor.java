package df.base.common.processing;

public interface Interceptor<C extends ProcessingContext, T> {
    void intercept(T target, C context);
}
