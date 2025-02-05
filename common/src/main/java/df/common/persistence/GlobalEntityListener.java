package df.common.persistence;

import org.jmouse.core.observer.EventManager;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jmouse.core.observer.EventManagerFactory;

import java.lang.annotation.Annotation;

@SuppressWarnings({"unused"})
public class GlobalEntityListener {

    private final static Logger          LOGGER           = LoggerFactory.getLogger(GlobalEntityListener.class);
    private final static String          LOGGER_TEMPLATE  = "EVENT '{}' CALLED FOR '{}' ENTITY";
    private final        EventManager    eventManager     = EventManagerFactory.create();

    @PrePersist
    public void onPrePersist(Object entity) {
        log(PrePersist.class, entity);
        eventManager.notify(new PersistenceEvent("prePersist", entity, this));
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        log(PostPersist.class, entity);
        eventManager.notify(new PersistenceEvent("postPersist", entity, this));
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        log(PreUpdate.class, entity);
        eventManager.notify(new PersistenceEvent("preUpdate", entity, this));
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        log(PostUpdate.class, entity);
        eventManager.notify(new PersistenceEvent("postUpdate", entity, this));
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        log(PreRemove.class, entity);
        eventManager.notify(new PersistenceEvent("preRemove", entity, this));
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        log(PostRemove.class, entity);
        eventManager.notify(new PersistenceEvent("postRemove", entity, this));
    }

    private void log(Class<? extends Annotation> eventType, Object entity) {
        LOGGER.info(LOGGER_TEMPLATE, eventType.getName(), entity.getClass().getName());
    }

}
