package df.base.common.elements.builder;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class BuilderContext {

    private final Map<Object, Object> attributes = new HashMap<>();

    public <T> T getAttribute(Class<T> classType) {
        Object value = attributes.get(classType);

        if (value == null || !classType.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Incorrect value");
        }

        return (T) value;
    }

    public <T> T getAttribute(Object key) {
        return (T) attributes.get(key);
    }

    public void setAttribute(Object key, Object value) {
        attributes.put(key, value);
    }

    public BuilderStrategy getStrategy() {
        return requireNonNull(this.getAttribute(BuilderStrategy.class));
    }

    public void setStrategy(BuilderStrategy strategy) {
        this.setAttribute(BuilderStrategy.class, strategy);
    }

}
