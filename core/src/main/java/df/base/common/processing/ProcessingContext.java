package df.base.common.processing;

import java.util.Map;

public interface ProcessingContext {

    <I extends Interceptor<?, ?>> void addInterceptor(Class<I> type, I interceptor);

    <I extends Interceptor<?, ?>> I getInterceptor(Class<I> type);

    Map<Object, Object> getProperties();

    void setProperty(Object key, Object value);

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

}
