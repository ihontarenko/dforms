package df.base.persistence.handler;

import df.base.common.commans.CommandRequest;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;

@Command("config")
@SuppressWarnings({"unused"})
public class FieldConfigurationProcess {

    @Action({"process"})
    public void processConfiguration(String action, CommandRequest request) {
        request.context(); // get repository bean to store datum
        System.out.println(action);
    }

}
