package df.common.persistence;

import org.jmouse.core.events.AbstractEvent;

public class PersistenceEvent extends AbstractEvent<Object> {

    public PersistenceEvent(String name, Object payload, Object caller) {
        super(name, payload, caller);
    }

}
