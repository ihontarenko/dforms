package df.base.common.context;

import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNullElse;
import static java.util.Optional.ofNullable;

abstract public class AbstractContext implements Context {

    private final Map<Object, Object> properties = new HashMap<>();
    private       BeanProvider        beanProvider;
    private       boolean             stopped    = false;

    public AbstractContext() {
        this(null);
    }

    public AbstractContext(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }

    @Override
    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }

    @Override
    public BeanProvider getBeanProvider() {
        return beanProvider;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return ofNullable(beanProvider).orElseThrow(() -> new MissingBeanProviderException(
                "The BeanProvider has not been provided in this context. Ensure it is set before usage.")).getBean(
                        beanClass);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return ofNullable(beanProvider).orElseThrow(() -> new MissingBeanProviderException(
                "The BeanProvider has not been provided in this context. Ensure it is set before usage.")).getBean(
                        beanName, beanClass);
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
        Class<?> classKey = ClassUtils.getUserClass(requireNonNullElse(value, new Object()).getClass());
        setProperty(classKey, value);
    }

    @Override
    public <R> R getProperty(Object key) {
        return getProperty(key, null);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <R> R getProperty(Object key, Object defaultValue) {
        return (R) properties.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean hasProperty(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void stopProcessing() {
        stopped = true;
    }

}
