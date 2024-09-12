package df.base.common.elements.builder;

import java.util.HashMap;
import java.util.Map;

public class BuilderContext {

    private final Map<Object, Object> attributes = new HashMap<>();

    public <T> T getAttribute(Class<T> classType) {
        return (T) attributes.get(classType);
    }

    public <T> T getAttribute(Object key) {
        return (T) attributes.get(key);
    }

    public void setAttribute(Object key, Object value) {
        attributes.put(key, value);
    }

    public BuilderStrategy getStrategy() {
        return this.getAttribute(BuilderStrategy.class);
    }

    public void setStrategy(BuilderStrategy strategy) {
        this.setAttribute(BuilderStrategy.class, strategy);
    }

}
