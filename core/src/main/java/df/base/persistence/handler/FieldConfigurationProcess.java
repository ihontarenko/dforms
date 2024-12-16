package df.base.persistence.handler;

import df.base.common.commans.CommandRequest;
import df.base.common.commans.CommandsManager;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;
import df.base.persistence.entity.form.FieldConfig;

import java.util.Map;

@Command("config")
@SuppressWarnings({"unused"})
public class FieldConfigurationProcess {

    @Action({"process"})
    public void processConfiguration(String action, CommandRequest request) {
        FieldConfig     entity          = request.queryParameter("entity");
        CommandsManager commandsManager = request.queryParameter("manager");

        commandsManager.execute(entity.getConfigName(), entity.getConfigValue(), request.context());
    }

}
