package df.base.internal.spring.jpa.entity_graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

final public class ObjectsHolder {

    private ObjectsHolder() { }

    private static final ThreadLocal<Map<Class<?>, Object>> THREAD_LOCAL_MAP
                                                                   = ThreadLocal.withInitial(HashMap::new);
    private static final Logger                             LOGGER = LoggerFactory.getLogger(ObjectsHolder.class);

    public static void set(Object value) {
        Class<?>              key     = requireNonNull(value).getClass();
        Map<Class<?>, Object> storage = THREAD_LOCAL_MAP.get();

        storage.put(key, value);
    }

    public static <T> Optional<T> get(Class<?> key) {
        Map<Class<?>, Object> storage     = THREAD_LOCAL_MAP.get();
        Optional<Class<?>>    eligibleKey = storage.keySet().stream().filter(key::isAssignableFrom).findFirst();
        Class<?>              passedKey   = key;

        eligibleKey.ifPresent(k -> LOGGER.debug("OBJECTS_HOLDER[{}]: Actual key '{}' for '{}'",
                currentThread().getName(), k.getName(), passedKey.getName()));

        key = eligibleKey.orElse(key);

        return (Optional<T>) Optional.ofNullable(storage.get(key));
    }

    public static <T> Optional<T> getAndRemove(Class<?> key) {
        Optional<T> value = get(key);

        remove(key);

        return value;
    }

    public static void remove(Class<?> key) {
        THREAD_LOCAL_MAP.get().remove(key);
    }

    public static boolean exists(Class<?> key) {
        return THREAD_LOCAL_MAP.get().containsKey(key);
    }

}
