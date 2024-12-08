package df.base.common.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A manager for handling event subscriptions and notifications.
 * It allows subscribers to listen for specific event types and notifies them when those events occur.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Define custom event and listener implementations
 * public class MyEvent implements Event<String> {
 *     private final String name;
 *     private final String payload;
 *
 *     public MyEvent(String name, String payload) {
 *         this.name = name;
 *         this.payload = payload;
 *     }
 *
 *     @Override
 *     public String name() {
 *         return name;
 *     }
 *
 *     @Override
 *     public String payload() {
 *         return payload;
 *     }
 * }
 *
 * public class MyEventListener implements EventListener<String> {
 *     @Override
 *     public void update(Event<? super String> event) {
 *         System.out.println("Received event: " + event.name() + " with payload: " + event.payload());
 *     }
 * }
 *
 * // Create and use EventManager
 * public static void main(String[] args) {
 *     EventManager eventManager = new EventManager("event1", "event2");
 *
 *     MyEventListener listener = new MyEventListener();
 *     eventManager.subscribe("event1", listener);
 *
 *     MyEvent event = new MyEvent("event1", "Hello, World!");
 *     eventManager.notify(event);
 * }
 * }</pre>
 */
final public class EventManager {

    /**
     * A map that associates event types with lists of subscribed listeners.
     */
    private final Map<String, List<EventListener<?>>> listeners = new HashMap<>();

    /**
     * Constructs an EventManager with predefined event types.
     * Each event type will be initialized with an empty list of listeners.
     *
     * @param eventTypes the types of events that the manager will handle.
     */
    public EventManager(String... eventTypes) {
        for (String eventType : eventTypes) {
            listeners.put(eventType, new ArrayList<>());
        }
    }

    /**
     * Subscribes a listener to a specific event type.
     *
     * @param eventType the type of event to subscribe to.
     * @param listener  the listener to be notified when the event occurs.
     * @throws EventManagerException if the specified event type is not recognized.
     */
    public void subscribe(String eventType, EventListener<?> listener) {
        List<EventListener<?>> eventListeners = listeners.get(eventType);

        if (eventListeners == null) {
            throw new EventManagerException(
                    "The event type '%s' is not registered in this EventManager.".formatted(eventType));
        }

        eventListeners.add(listener);
    }

    /**
     * Unsubscribes a listener from a specific event type.
     *
     * @param eventType the type of event to unsubscribe from.
     * @param listener  the listener to be removed.
     */
    public void unsubscribe(String eventType, EventListener<?> listener) {
        List<EventListener<?>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
            if (eventListeners.isEmpty()) {
                unsubscribe(eventType);
            }
        }
    }

    /**
     * Removes all listeners subscribed to a specific event type.
     *
     * @param eventType the type of event to clear all subscriptions for.
     */
    public void unsubscribe(String eventType) {
        listeners.remove(eventType);
    }

    /**
     * Notifies all listeners subscribed to the specified event type.
     *
     * @param event the event to be dispatched to listeners.
     * @param <T>   the type of the event payload.
     */
    @SuppressWarnings("unchecked")
    public <T> void notify(Event<T> event) {
        List<EventListener<?>> eventListeners = listeners.get(event.name());
        if (eventListeners != null) {
            for (EventListener<?> listener : eventListeners) {
                EventListener<T> typedListener = (EventListener<T>) listener;
                typedListener.update(event);
            }
        }
    }
}

