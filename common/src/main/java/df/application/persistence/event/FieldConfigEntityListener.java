package df.application.persistence.event;

import org.jmouse.core.events.AbstractEventListener;
import org.jmouse.core.events.Event;
import org.jmouse.core.events.annotation.Listener;
import df.application.persistence.entity.form.FieldConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unused"})
@Listener(events = {"preUpdate", "prePersist"})
public class FieldConfigEntityListener extends AbstractEventListener<FieldConfig> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FieldConfigEntityListener.class);

    @Override
    public void onEvent(Event<FieldConfig> event) {
        FieldConfig config = event.payload();
        String      name   = config.getConfigName();
        String      value  = config.getConfigValue();

        if (name.startsWith("#action")) {

        }
    }

    @Override
    public Class<?> payloadType() {
        return FieldConfig.class;
    }

}
