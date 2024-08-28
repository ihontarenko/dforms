package df.base.internal.jbm.event;

public interface EventListenerAware {

    void setEventListener(EventListener listener);

    EventListener getEventListener();

}
