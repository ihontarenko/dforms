package df.base.common.persistence.observer;

import df.base.common.commans.CommandsManager;
import df.base.common.commans.CommandsManagerFactory;
import df.base.common.observer.EventManager;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

@SuppressWarnings({"unused"})
public class GlobalEntityListener {

    private final static Logger          LOGGER                = LoggerFactory.getLogger(GlobalEntityListener.class);
    private final static String          LOGGER_TEMPLATE       = "EVENT '{}' CALLED FOR '{}' ENTITY";
    private final        EventManager    eventManager          = EventManager.INSTANCE;
    private final        CommandsManager operationManager      = CommandsManager.INSTANCE;

    @PrePersist
    public void onPrePersist(Object entity) {
        log(PrePersist.class, entity);
        eventManager.notify(new PersistenceEvent("pre_persist", entity));
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        log(PostPersist.class, entity);
        eventManager.notify(new PersistenceEvent("post_persist", entity));
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        log(PreUpdate.class, entity);
        eventManager.notify(new PersistenceEvent("pre_update", entity));
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        log(PostUpdate.class, entity);
        eventManager.notify(new PersistenceEvent("post_update", entity));
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        log(PreRemove.class, entity);
        eventManager.notify(new PersistenceEvent("pre_remove", entity));
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        log(PostRemove.class, entity);
        eventManager.notify(new PersistenceEvent(PostRemove.class, entity));
    }

    private void log(Class<? extends Annotation> eventType, Object entity) {
        LOGGER.info(LOGGER_TEMPLATE, eventType.getName(), entity.getClass().getName());
    }

}
