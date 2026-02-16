package df.common.persistence;

import org.jmouse.core.events.AbstractEvent;
import org.jmouse.core.events.EventCategory;
import org.jmouse.core.events.EventName;

public class PersistenceEvent extends AbstractEvent<Object> {

    public PersistenceEvent(String name, Object payload, Object caller) {
        super(EventName.of(name, EventCategory.UNCATEGORIZED), payload, caller);
    }

}
