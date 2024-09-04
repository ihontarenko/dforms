package df.base.common.libs.jbm.event;

public interface EventListenerAware {

    void setEventListener(EventListener listener);

    EventListener getEventListener();

}
