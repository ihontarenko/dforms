package df.base.common.commans;

import df.base.common.context.Context;
import df.base.common.context.ErrorDetails;
import df.base.common.invocable.Invocable;
import df.base.common.invocable.InvokeResult;
import df.base.common.invocable.Invoker;
import df.base.common.invocable.MethodParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code CommandsManager} is a centralized manager for registering and executing commands and their associated actions.
 * It supports mapping commands to actions and invoking them with a given {@link Context} and parameters.
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * // Register a command with an action
 * CommandsManager.INSTANCE.register("commandName", "actionName", new MyInvocable());
 *
 * // Execute a command with definition and parameters
 * Context context = new MyContext();
 * String result = CommandsManager.INSTANCE.execute("commandName/actionName", "param1=value1", context);
 * }</pre>
 */
public class CommandsManager {

    /**
     * Singleton instance of the {@code CommandsManager} created by the {@link CommandsManagerFactory}.
     */
    public final static CommandsManager INSTANCE = CommandsManagerFactory.create();

    /**
     * Stores registered commands mapped to their associated actions and invocables.
     */
    private final Map<String, Map<String, Invocable>> commands = new HashMap<>();

    /**
     * Registers a command and its associated action.
     *
     * @param command   the name of the command
     * @param action    the name of the action
     * @param invocable the {@link Invocable} instance to associate with the command/action pair
     * @throws RouteDuplicationException if the command/action pair is already registered with another invocable
     */
    public void register(String command, String action, Invocable invocable) {
        Map<String, Invocable> invocables = commands.computeIfAbsent(command, k -> new HashMap<>());

        if (invocables.containsKey(action)) {
            Invocable presentHandler = getInvocableHandler(command, action);
            throw new RouteDuplicationException(
                    "The route '%s/%s' is already handled by '%s'."
                            .formatted(command, action, presentHandler.getDescriptor().getDetailedName()));
        }

        invocables.put(action, invocable);
    }

    /**
     * Executes a command with the specified definition and parameters within a given context.
     *
     * @param definition the command definition in the format "command/action"
     * @param parameters the parameters for the command
     * @param context    the {@link Context} in which to execute the command
     * @param <T>        the expected return type of the command execution
     * @return the result of the command execution
     */
    public <T> T execute(String definition, String parameters, Context context) {
        return execute(new CommandRequestBuilder(definition, parameters).build(context));
    }

    /**
     * Executes a command based on a {@link CommandRequest}.
     *
     * @param request the {@link CommandRequest} containing details of the command to execute
     * @param <T>     the expected return type of the command execution
     * @return the result of the command execution
     * @throws RouteNotFoundException   if the specified command or action is not registered
     * @throws CommandExecutionException if an error occurs during command execution
     */
    public <T> T execute(CommandRequest request) {
        InvokeResult result = invokeHandler(request);

        if (result.hasErrors()) {
            ErrorDetails errorDetails = result.getError(Invoker.ERROR_CODE);

            if (errorDetails != null) {
                throw new CommandExecutionException(errorDetails.message(), errorDetails.cause());
            }

            throw new CommandExecutionException("An unknown error occurred during command invocation.");
        }

        return result.getReturnValue();
    }

    /**
     * Retrieves the {@link Invocable} handler for a given command and action from a {@link CommandRequest}.
     *
     * @param request the {@link CommandRequest} specifying the command and action
     * @return the {@link Invocable} handler associated with the command and action
     * @throws RouteNotFoundException if the command or action is not found
     */
    public Invocable getInvocableHandler(CommandRequest request) {
        return getInvocableHandler(request.command(), request.action());
    }

    /**
     * Retrieves the {@link Invocable} handler for a specific command and action.
     *
     * @param command the name of the command
     * @param action  the name of the action
     * @return the {@link Invocable} handler associated with the command and action
     * @throws RouteNotFoundException if the command or action is not found
     */
    public Invocable getInvocableHandler(String command, String action) {
        Map<String, Invocable> actions = commands.get(command);

        if (actions == null) {
            throw new RouteNotFoundException("No command found for '%s'.".formatted(command));
        }

        Invocable invocable = actions.get(action);

        if (invocable == null) {
            throw new RouteNotFoundException(
                    "No handler named '%s' is registered under the command '%s'.".formatted(action, command));
        }

        return invocable;
    }

    /**
     * Invokes the {@link Invocable} handler for the specified {@link CommandRequest}.
     *
     * @param request the {@link CommandRequest} specifying the command and action to invoke
     * @return the {@link InvokeResult} of the command invocation
     */
    public InvokeResult invokeHandler(CommandRequest request) {
        Invocable invocable = getInvocableHandler(request);

        // clean-up previous parameters and assign the new parameter-value
        invocable.cleanupParameters();

        invocable.addParameter(new MethodParameter(0, request.action()));
        invocable.addParameter(new MethodParameter(1, request));

        return invocable.invoke();
    }

    public static CommandsManager getInstance() {
        return INSTANCE;
    }

}
