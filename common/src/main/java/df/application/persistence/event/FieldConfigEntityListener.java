package df.application.persistence.event;

import df.application.PackageCoreRoot;
import df.application.provider.SpringBeanLookup;
import org.jmouse.action.ActionExecutionContext;
import org.jmouse.action.ActionExecutor;
import org.jmouse.action.ActionExecutorSingleton;
import org.jmouse.core.events.AbstractEventListener;
import org.jmouse.core.events.Event;
import org.jmouse.core.events.annotation.Listener;
import df.application.persistence.entity.form.FieldConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unused"})
@Listener(events = {"preUpdate", "prePersist"})
public class FieldConfigEntityListener extends AbstractEventListener<FieldConfig> {

    private final static Logger         LOGGER          = LoggerFactory.getLogger(FieldConfigEntityListener.class);
    private final static ActionExecutor ACTION_EXECUTOR = ActionExecutorSingleton.getActionExecutor(
            PackageCoreRoot.class);

    @Override
    public void onEvent(Event<FieldConfig> event) {
        FieldConfig config = event.payload();
        String      name   = config.getConfigName();
        String      value  = config.getConfigValue();

        if (name.startsWith("#action")) {
            ActionExecutionContext context = new ActionExecutionContext();
            context.setProperty("entity", config);
            context.setBeanLookup(new SpringBeanLookup());
            ActionExecutorSingleton.getActionExpressionAdapter(ACTION_EXECUTOR).execute(value, context);
        }
    }

    @Override
    public Class<?> payloadType() {
        return FieldConfig.class;
    }

}
