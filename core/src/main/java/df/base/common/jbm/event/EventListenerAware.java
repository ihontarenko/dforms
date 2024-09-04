package df.base.common.jbm.event;

public interface EventListenerAware {

    void setEventListener(EventListener listener);

    EventListener getEventListener();

}
