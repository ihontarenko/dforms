package df.base.common.application_context.event;

public interface EventListenerAware {

    void setEventListener(EventListener listener);

    EventListener getEventListener();

}
