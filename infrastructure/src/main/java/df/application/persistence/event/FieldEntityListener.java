package df.application.persistence.event;

import org.jmouse.core.observer.AbstractEventListener;
import org.jmouse.core.observer.Event;
import org.jmouse.core.observer.annotation.Listener;
import df.application.persistence.entity.form.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listener(events = {"PreUpdate"})
public class FieldEntityListener extends AbstractEventListener<Field> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FieldEntityListener.class);

    @Override
    public void update(Event<Field> event) {
        Field entity = event.payload();

        System.out.println(entity);

        LOGGER.info("EVENT '{}' FOR FORM-ENTITY TRIGGERED", event.name());
    }

    @Override
    public Class<?> applicableType() {
        return Field.class;
    }
}
