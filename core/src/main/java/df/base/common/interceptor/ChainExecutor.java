package df.base.common.interceptor;

public interface ChainExecutor<T> {
    void proceed(T target);
}
