package df.base.common.observer;

import df.base.PackageCoreRoot;
import df.base.common.matcher.reflection.TypeMatchers;
import df.base.common.observer.annotation.Listener;
import df.base.common.reflection.ClassFinder;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

import static df.base.common.reflection.Reflections.findFirstConstructor;
import static df.base.common.reflection.Reflections.instantiate;

/**
 * Factory class for creating and configuring {@link EventManager} instances.
 *
 * <p>This class provides methods to create default or customized {@link EventManager} objects.
 * It can scan for annotated classes, validate their compatibility, and automatically register event listeners.</p>
 *
 * <h3>Usage Examples:</h3>
 *
 * <pre>{@code
 * // Create a default EventManager
 * EventManager defaultManager = EventManagerFactory.createDefault();
 *
 * // Create an EventManager with listeners annotated by a custom annotation
 * EventManager customManager = EventManagerFactory.create(MyCustomAnnotation.class);
 *
 * // Create and customize an EventManager using a configurator
 * EventManager configuredManager = EventManagerFactory.create(eventManager -> {
 *     eventManager.subscribe("event1", new MyEventListener());
 * });
 * }</pre>
 */
public class EventManagerFactory {

    /**
     * Creates a default {@link EventManager} instance with no pre-configured listeners.
     *
     * @return a new instance of {@link EventManager}.
     */
    public static EventManager createDefault() {
        return new EventManager();
    }

    /**
     * Creates an {@link EventManager} instance and automatically registers event listeners
     * for classes annotated with the specified annotation type.
     *
     * <p>Annotated classes must implement the {@link EventListener} interface. If a class does not
     * implement the required interface, an {@link ObserverException} is thrown.</p>
     *
     * @param annotationType the annotation type to scan for event listeners.
     * @return a configured {@link EventManager} with registered listeners.
     * @throws ObserverException if an annotated class does not implement {@link EventListener}.
     */
    public static EventManager create(Class<? extends Annotation> annotationType) {
        return create(eventManager -> {
            for (Class<?> annotatedClass : ClassFinder.findAnnotatedClasses(annotationType, PackageCoreRoot.class)) {
                if (TypeMatchers.isSupertype(EventListener.class).matches(annotatedClass)) {
                    EventListener<?> eventListener = (EventListener<?>) instantiate(findFirstConstructor(annotatedClass));
                    Listener annotation = annotatedClass.getAnnotation(Listener.class);
                    for (String event : annotation.events()) {
                        eventManager.subscribe(event, eventListener);
                    }
                } else {
                    throw new ObserverException(
                            "Annotated event-listener '%s' is required to implement the interface '%s'"
                                    .formatted(annotatedClass, EventListener.class));
                }
            }
        });
    }

    /**
     * Creates an {@link EventManager} instance and applies a custom configurator function
     * to register event listeners or modify its configuration.
     *
     * @param configurator a {@link Consumer} function to configure the {@link EventManager}.
     * @return a configured {@link EventManager} instance.
     */
    public static EventManager create(Consumer<EventManager> configurator) {
        EventManager eventManager = createDefault();

        configurator.accept(eventManager);

        return eventManager;
    }

}
