package df.base.persistence;

import df.base.common.operation.OperationManager;
import df.base.common.operation.OperationManagerFactory;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class GlobalEntityListener {

    private final static String           OPERATION_PRE_UPDATE  = "#persistence:pre_update";
    private final static String           OPERATION_PRE_PERSIST = "#persistence:pre_persist";
    private final static String           LOGGER_TEMPLATE       = "EVENT '{}' CALLED FOR '{}' ENTITY";
    private final static Logger           LOGGER                = LoggerFactory.getLogger(GlobalEntityListener.class);
    private final        OperationManager operationManager      = OperationManagerFactory.createWithAnnotatedOperator();

    @PrePersist
    public void onPrePersist(Object entity) {
        log(PrePersist.class, entity);
        operationManager.execute(OPERATION_PRE_PERSIST, "", entity);
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        log(PostPersist.class, entity);
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        log(PreUpdate.class, entity);
        operationManager.execute(OPERATION_PRE_UPDATE, "", entity);
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        log(PostUpdate.class, entity);
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        log(PreRemove.class, entity);
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        log(PostRemove.class, entity);
    }

    private void log(Class<? extends Annotation> eventType, Object entity) {
        LOGGER.info(LOGGER_TEMPLATE, eventType.getName(), entity.getClass().getName());
    }

}
