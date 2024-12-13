package df.base.persistence.handler;

import df.base.common.commans.CommandRequest;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;
import df.base.persistence.entity.form.FieldConfig;
import df.base.persistence.entity.form.FieldOption;

import java.util.Set;

@SuppressWarnings({"unused"})
@Command("action")
public class FieldDataLoadHandler {

    @Action({"data_load"})
    public void preUpdate(String actionName, CommandRequest request) {
        if (request.parameter("config") instanceof FieldConfig config) {
            Set<FieldOption> options = config.getField().getOptions();

            if (request.parameter("class") instanceof Class<?> dataProviderClass) {

            }

            options.clear();
        }
    }

}
