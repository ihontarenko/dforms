package df.application.persistence.event;

import org.jmouse.core.observer.AbstractEventListener;
import org.jmouse.core.observer.Event;
import org.jmouse.core.observer.annotation.Listener;
import df.application.persistence.entity.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unused"})
@Listener(events = {"preUpdate"})
public class FormEntityListener extends AbstractEventListener<Form> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FormEntityListener.class);

    @Override
    public void update(Event<Form> event) {
        Form form = event.payload();

        System.out.println(form);

        LOGGER.info("EVENT '{}' FOR FIELD", event.name());
    }

    @Override
    public Class<?> applicableType() {
        return Form.class;
    }
}
