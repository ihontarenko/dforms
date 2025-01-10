package df.common.persistence;

import svit.observer.AbstractEvent;

public class PersistenceEvent extends AbstractEvent<Object> {

    public PersistenceEvent(String name, Object payload, Object caller) {
        super(name, payload, caller);
    }

}
