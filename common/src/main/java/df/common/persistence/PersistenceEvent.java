package df.common.persistence;

import org.jmouse.core.observer.AbstractEvent;

public class PersistenceEvent extends AbstractEvent<Object> {

    public PersistenceEvent(String name, Object payload, Object caller) {
        super(name, payload, caller);
    }

}
