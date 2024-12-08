package df.base.common.observer;

public interface EventListener<T> {
    void update(Event<T> event);
}
