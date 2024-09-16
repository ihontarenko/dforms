package df.base.common.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractProcessingContext implements ProcessingContext {

    private final Map<Object, Object>              properties   = new HashMap<>();
    private final Map<Class<?>, Interceptor<?, ?>> interceptors = new HashMap<>();

    public AbstractProcessingContext() {
        initialize();
    }

    public <I extends Interceptor<?, ?>> void addInterceptor(Class<I> type, I interceptor) {
        interceptors.put(type, interceptor);
    }

    public <I extends Interceptor<?, ?>> I getInterceptor(Class<I> type) {
        return type.cast(interceptors.get(type));
    }

    @Override
    public Map<Object, Object> getProperties() {
        return properties;
    }

    @Override
    public void setProperty(Object key, Object value) {
        properties.put(key, value);
    }

    @Override
    public void setProperty(Object value) {
        setProperty(Objects.requireNonNullElse(value, new Object()).getClass(), value);
    }

    @Override
    public <R> R getProperty(Object key) {
        return getProperty(key, null);
    }

    @Override
    public <R> R getProperty(Object key, Object defaultValue) {
        return (R) properties.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean hasProperty(Object key) {
        return properties.containsKey(key);
    }

    abstract protected void initialize();

}
