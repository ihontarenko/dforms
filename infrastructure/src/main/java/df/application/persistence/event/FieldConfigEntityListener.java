package df.application.persistence.event;

import df.application.Instances;
import df.common.commans.CommandsManager;
import svit.support.context.Context;
import svit.observer.AbstractEventListener;
import svit.observer.Event;
import svit.observer.annotation.Listener;
import df.application.persistence.entity.form.FieldConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static df.common.commans.CommandExecutionContext.create;

@SuppressWarnings({"unused"})
@Listener(events = {"preUpdate", "prePersist"})
public class FieldConfigEntityListener extends AbstractEventListener<FieldConfig> {

    private final static Logger          LOGGER          = LoggerFactory.getLogger(FieldConfigEntityListener.class);
    private final        CommandsManager commandsManager = Instances.COMMANDS_MANAGER;

    @Override
    public void update(Event<FieldConfig> event) {
        Context context = create(Map.of("entity", event.payload(), "manager", commandsManager, "event", event));
        commandsManager.execute("#config/process", "(entity=#entity, manager=#manager)", context);
    }

    @Override
    public Class<?> applicableType() {
        return FieldConfig.class;
    }

}
