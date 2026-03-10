package df.application.persistence.event;

import df.application.Instances;
import df.common.commans.CommandsManager;
import org.jmouse.core.scope.Context;
import org.jmouse.core.events.AbstractEventListener;
import org.jmouse.core.events.Event;
import org.jmouse.core.events.annotation.Listener;
import df.application.persistence.entity.form.FieldConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static df.common.commans.CommandExecutionContext.create;

@SuppressWarnings({"unused"})
@Listener(events = {"preUpdate", "prePersist"})
public class FieldConfigEntityListener extends AbstractEventListener<FieldConfig> {

    private final static Logger          LOGGER          = LoggerFactory.getLogger(FieldConfigEntityListener.class);

    @Override
    public void onEvent(Event<FieldConfig> event) {
        Context context = create(Map.of("entity", event.payload(), "manager", Instances.COMMANDS_MANAGER, "event", event));
        Instances.COMMANDS_MANAGER.execute("#config/process", "(entity=#entity, manager=#manager)", context);
    }

    @Override
    public Class<?> payloadType() {
        return FieldConfig.class;
    }

}
