package df.base.common.persistence.observer;

import df.base.common.observer.AbstractEvent;

import java.lang.annotation.Annotation;

public class PersistenceEvent extends AbstractEvent<Object> {

    public PersistenceEvent(String name, Object payload, Object caller) {
        super(name, payload, caller);
    }

}
