package df.base.common.context;

import java.util.Map;

@SuppressWarnings({"unused"})
public interface Context extends BeanProvider, BeanProviderAware {

    Map<Object, Object> getProperties();

    default void copyFrom(Context context) {
        context.getProperties().forEach(this::setProperty);
        setBeanProvider(context.getBeanProvider());
    }

    default void copyTo(Context context) {
        getProperties().forEach(context::setProperty);
        context.setBeanProvider(getBeanProvider());
    }

    void setProperty(Object key, Object value);

    default void setProperties(Map<Object, Object> properties) {
        properties.forEach(this::setProperty);
    }

    void setProperty(Object value);

    <R> R getProperty(Object key);

    <R> R getProperty(Object key, Object defaultValue);

    default Object requireProperty(Object key) {
        Object property = getProperty(key);

        if (property == null) {
            throw new DefaultContextException(
                    "Context does not contain the required property '%s'.".formatted(key));
        }

        return property;
    }

    boolean hasProperty(Object key);

    boolean isStopped();

    void stopProcessing();

}
