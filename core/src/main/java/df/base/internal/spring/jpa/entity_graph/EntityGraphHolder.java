package df.base.internal.spring.jpa.entity_graph;

public class EntityGraphHolder {

    public static void set(Object value) {

    }

    public static <T> T get() {
        return null;
    }

    public static void remove() {

    }

    public static <T> T getAndRemove() {
        T value = get();

        remove();

        return value;
    }

}
