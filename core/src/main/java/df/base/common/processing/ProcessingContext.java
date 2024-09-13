package df.base.common.processing;

import java.util.Map;

public interface ProcessingContext<T> {

    T getTarget();

    Map<Object, Object> getProperties();

    void setProperty(Object key, Object value);

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

}
