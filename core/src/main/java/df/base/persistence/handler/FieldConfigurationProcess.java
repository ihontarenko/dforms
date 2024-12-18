package df.base.persistence.handler;

import df.base.common.commans.CommandRequest;
import df.base.common.commans.CommandsManager;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;
import df.base.common.context.Context;
import df.base.common.observer.Event;
import df.base.common.specification.SpecificationContext;
import df.base.common.specification.SpecificationRunner;
import df.base.persistence.entity.form.FieldConfig;

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
