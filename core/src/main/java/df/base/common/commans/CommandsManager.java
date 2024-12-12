package df.base.common.commans;

import df.base.common.context.Context;

import java.util.HashMap;
import java.util.Map;

public class CommandsManager {

    public final static CommandsManager                            INSTANCE  = CommandsManagerFactory.create();
    private final       Map<String, Map<String, ActionHandler<?>>> operators = new HashMap<>();

    /**
     * Registers an operator for a specific controller and action.
     *
     * @param operation The name of the controller.
     * @param action    The name of the action associated with the controller.
     * @param operator  The operator to handle the action for the controller.
     */
    public void register(String operation, String action, ActionHandler<?> operator) {
        operators.computeIfAbsent(operation, k -> new HashMap<>()).put(action, operator);
    }

    /**
     * Executes an controller based on a definition string, raw parameters, and a target object.
     * The definition is parsed to extract the controller and action details, and the raw parameters
     * are parsed and validated before execution.
     *
     * @param definition A string representing the controller definition.
     * @param parameters A string representing the raw parameters to be parsed and passed to the controller.
     * @param target     The target object on which the controller will be executed.
     * @param <T>        The return type of the controller.
     * @return The result of executing the operator's handle method.
     * @throws NoCommandFoundException If there are errors during parsing or execution.
     */
    public <T> T execute(String definition, String parameters, Context context) {
        return execute(new CommandRequestBuilder(definition, parameters).build(context));
    }

    /**
     * Executes a controller request by finding the appropriate operator and invoking its handle method.
     *
     * @param request The controller request containing the controller, action, and parameters.
     * @param <T>     The return type of the controller.
     * @return The result of executing the operator's handle method.
     * @throws NoCommandFoundException If no operator is found for the requested controller or action.
     */
    @SuppressWarnings("unchecked")
    public <T> T execute(CommandRequest request) {
        CommandRoute                  commandRoute = request.route();
        Map<String, ActionHandler<?>> actions      = operators.get(commandRoute.command());

        if (actions == null) {
            throw new NoCommandFoundException(
                    "NO COMMAND ARE REGISTERED '%s'.".formatted(commandRoute.command()));
        }

        ActionHandler<T> actionHandler = (ActionHandler<T>) actions.get(commandRoute.action());

        if (actionHandler == null) {
            throw new NoCommandFoundException(
                    "NO ACTION IS REGISTERED FOR THE NAME '%s' UNDER THE CONTROLLER '%s'."
                            .formatted(commandRoute.action(), commandRoute.command()));
        }

        return actionHandler.handle(request);
    }
}
