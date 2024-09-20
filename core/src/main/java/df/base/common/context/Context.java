package df.base.common.context;

import java.util.Map;

@SuppressWarnings({"unused"})
public interface Context extends BeanProvider, BeanProviderAware {

    Map<Object, Object> getProperties();

    void setProperty(Object key, Object value);

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

    boolean hasProperty(Object key);

    boolean isStopped();

    void stopProcessing();

}
