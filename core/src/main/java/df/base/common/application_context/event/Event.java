package df.base.common.application_context.event;

public interface Event {

    EventType getEventType();

    Object getEventData();

}
