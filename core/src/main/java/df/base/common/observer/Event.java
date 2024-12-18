package df.base.common.observer;

public interface Event<T> {

    String name();

    T payload();

    Class<? extends T> payloadType();

    Object caller();

}
