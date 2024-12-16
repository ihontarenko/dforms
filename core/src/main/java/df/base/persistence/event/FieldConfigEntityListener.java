package df.base.persistence.event;

import df.base.common.commans.CommandsManager;
import df.base.common.context.Context;
import df.base.common.observer.AbstractEventListener;
import df.base.common.observer.Event;
import df.base.common.observer.annotation.Listener;
import df.base.persistence.entity.form.FieldConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static df.base.common.commans.CommandExecutionContext.create;

@SuppressWarnings({"unused"})
@Listener(events = {"pre_update", "pre_persist"})
public class FieldConfigEntityListener extends AbstractEventListener<FieldConfig> {

    private final static Logger          LOGGER          = LoggerFactory.getLogger(FieldConfigEntityListener.class);
    private final        CommandsManager commandsManager = CommandsManager.getInstance();

    @Override
    public void update(Event<FieldConfig> event) {
        Context context = create(Map.of("entity", event.payload(), "manager", commandsManager));
        commandsManager.execute("#config/process", "(entity=#entity, manager=#manager)", context);
    }

    @Override
    public Class<?> applicableType() {
        return FieldConfig.class;
    }

}
