package df.base.common.processing;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractProcessingContext<T> implements ProcessingContext<T> {

    private T target;
    private final Map<Object, Object> properties = new HashMap<>();

    public AbstractProcessingContext(T target) {
        this.target = target;
    }

    public T getTarget() {
        return target;
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

}
