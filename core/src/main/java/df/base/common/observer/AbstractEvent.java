package df.base.common.observer;

import java.util.Objects;

abstract public class AbstractEvent<T> implements Event<T> {

    protected final String name;
    protected final T      payload;

    public AbstractEvent(String name, T payload) {
        this.name = name;
        this.payload = payload;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public T payload() {
        return payload;
    }

    @Override
    public Class<? extends T> payloadType() {
        return (Class<? extends T>) Objects.requireNonNull(payload()).getClass();
    }
}
