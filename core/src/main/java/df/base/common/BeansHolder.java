package df.base.common;

import java.util.HashMap;
import java.util.Map;

public enum BeansHolder {

    INSTANCE;

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public boolean has(Class<?> type) {
        return beans.containsKey(type);
    }

    public <T> T get(Class<T> type) {
        return type.cast(beans.get(type));
    }

    public <T> void set(Class<T> type, T bean) {
        beans.put(type, bean);
    }

}
