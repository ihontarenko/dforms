package df.application.persistence.handler;

import df.common.commans.CommandRequest;
import df.common.commans.CommandsManager;
import df.common.commans.annotation.Action;
import df.common.commans.annotation.Command;
import org.jmouse.common.support.context.Context;
import org.jmouse.core.events.Event;
import df.common.specification.SpecificationContext;
import df.common.specification.SpecificationRunner;
import df.application.persistence.entity.form.FieldConfig;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Command("config")
@SuppressWarnings({"unused"})
public class FieldConfigurationProcess {

    public static final List<String> ALLOWED_ACTIONS = List.of("#action/load");

    @Action({"process"})
    public void processConfiguration(String action, CommandRequest request) {
        FieldConfig     entity          = request.queryParameter("entity");
        CommandsManager commandsManager = request.queryParameter("manager");
        Context         context         = request.context();

        SpecificationContext.Builder builder = new SpecificationContext.Builder()
                .with("allowedActions", ALLOWED_ACTIONS);

        if (context.hasProperty("event") && context.getProperty("event") instanceof Event<?> event) {
            String contextName = "%s:%s".formatted(requireNonNull(event.caller()).getClass().getSimpleName(), event.name());
            builder.with("contextName", contextName);
        }

        new SpecificationRunner<FieldConfig>()
                .checkAllSatisfied(entity, builder.build(), new AllowedActionSpecification());

        commandsManager.execute(entity.getConfigName(), entity.getConfigValue(), request.context());
    }

}
