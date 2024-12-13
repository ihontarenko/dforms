package df.base.common.commans;

import static df.base.common.commans.CommandExecutionContext.create;

public class Example {

    public static void main(String[] args) {

        CommandsManager manager = CommandsManager.INSTANCE;

        String request = "#validation/not_null";
        String parameters = "(message=#root)";

        System.out.println(manager.execute(request, parameters, create("root", "message")).toString());
        System.out.println(manager.execute(request, parameters, create("root", "another message")).toString());

    }

}
