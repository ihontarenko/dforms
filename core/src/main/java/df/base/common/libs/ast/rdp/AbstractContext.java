package df.base.common.libs.ast.rdp;

import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNullElse;

abstract public class AbstractContext {

    private final Map<Object, Object> properties = new HashMap<>();

    public Map<Object, Object> getProperties() {
        return properties;
    }

    public void setProperty(Object key, Object value) {
        properties.put(key, value);
    }

    public void setProperty(Object value) {
        Class<?> classKey = ClassUtils.getUserClass(requireNonNullElse(value, new Object()).getClass());
        setProperty(classKey, value);
    }

    public <R> R getProperty(Object key) {
        return getProperty(key, null);
    }

    public <R> R getProperty(Object key, Object defaultValue) {
        return (R) properties.getOrDefault(key, defaultValue);
    }

    public <T> T requireProperty(Object name) {
        if (hasProperty(name)) {
            return getProperty(name);
        }

        throw new ContextPropertyNotFoundException("Required property not found [%s]".formatted(name));
    }

    public boolean hasProperty(Object key) {
        return properties.containsKey(key);
    }

}
