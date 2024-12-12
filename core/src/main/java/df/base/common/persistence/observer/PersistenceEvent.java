package df.base.common.persistence.observer;

import df.base.common.observer.AbstractEvent;

import java.lang.annotation.Annotation;

public class PersistenceEvent extends AbstractEvent<Object> {

    public PersistenceEvent(Class<? extends Annotation> eventType, Object payload) {
        super(eventType.getSimpleName(), payload);
    }

    public PersistenceEvent(String name, Object payload) {
        super(name, payload);
    }

}
