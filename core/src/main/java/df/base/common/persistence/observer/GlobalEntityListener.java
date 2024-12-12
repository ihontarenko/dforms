package df.base.common.persistence.observer;

import df.base.common.commans.CommandsManager;
import df.base.common.commans.CommandsManagerFactory;
import df.base.common.observer.EventManager;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

import static df.base.common.commans.CommandExecutionContext.create;

@SuppressWarnings({"unused"})
public class GlobalEntityListener {

    private final static String          OPERATION_PRE_UPDATE  = "#persistence:pre_update";
    private final static String          OPERATION_PRE_PERSIST = "#persistence:pre_persist";
    private final static Logger          LOGGER                = LoggerFactory.getLogger(GlobalEntityListener.class);
    private final static String          LOGGER_TEMPLATE       = "EVENT '{}' CALLED FOR '{}' ENTITY";
    private final        CommandsManager operationManager      = CommandsManagerFactory.create();
    private final        EventManager    eventManager          = EventManager.INSTANCE;

    @PrePersist
    public void onPrePersist(Object entity) {
        log(PrePersist.class, entity);
        eventManager.notify(new PersistenceEvent(PrePersist.class, entity));
        operationManager.execute(OPERATION_PRE_PERSIST, "", create("entity", entity));
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        log(PostPersist.class, entity);
        eventManager.notify(new PersistenceEvent(PostPersist.class, entity));
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        log(PreUpdate.class, entity);
        eventManager.notify(new PersistenceEvent(PreUpdate.class, entity));
        operationManager.execute(OPERATION_PRE_UPDATE, "", create("entity", entity));
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        log(PostUpdate.class, entity);
        eventManager.notify(new PersistenceEvent(PostUpdate.class, entity));
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        log(PreRemove.class, entity);
        eventManager.notify(new PersistenceEvent(PreRemove.class, entity));
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
