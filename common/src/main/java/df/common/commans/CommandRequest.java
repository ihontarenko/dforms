package df.common.commans;

import org.jmouse.common.support.context.Context;

import java.util.Map;

/**
 * {@code CommandRequest} represents a request to execute a command, encapsulating the command route
 * and associated query parameters.
 *
 * <p>It provides methods to access command and action details, as well as any query parameters
 * required for execution. The interface also includes a static factory method for creating
 * instances using a default implementation.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CommandRoute route = new CommandRoute("commandName", "actionName");
 * Map<String, Object> queryParameters = Map.of("param1", "value1", "param2", "value2");
 * CommandRequest request = CommandRequest.create(route, queryParameters);
 *
 * String command = request.command();
 * String action = request.action();
 * Object paramValue = request.parameter("param1");
 * }</pre>
 */
public interface CommandRequest {

    /**
     * Creates a new {@code CommandRequest} instance with the given route and query parameters.
     *
     * @param route           the {@link CommandRoute} specifying the command and action
     * @param queryParameters a map of query parameters to include in the request
     * @return a new {@code CommandRequest} instance
     */
    static CommandRequest create(CommandRoute route, Map<String, Object> queryParameters, Context context) {
        return new CommandRequestDefaultImplementation(route, queryParameters, context);
    }

    /**
     * Retrieves the command name from the route.
     *
     * @return the name of the command
     */
    default String command() {
        return route().command();
    }

    /**
     * Retrieves the action name from the route.
     *
     * @return the name of the action
     */
    default String action() {
        return route().action();
    }

    /**
     * Retrieves the {@link CommandRoute} associated with this request.
     *
     * @return the command route
     */
    CommandRoute route();

    /**
     * Retrieves the query parameters associated with this request.
     *
     * @return a map of query parameters
     */
    Map<String, Object> queryParameters();

    /**
     * Add the extra query parameters associated with this request.
     */
    default void queryParameters(Map<String, Object> parameters) {
        queryParameters().putAll(parameters);
    }

    /**
     * Retrieves the {@link Context} associated with this request.
     *
     * @return a {@link Context} structured
     */
    Context context();

    /**
     * Retrieves the value of a specific query parameter by name.
     *
     * @param parameterName the name of the query parameter
     * @return the value of the specified query parameter, or {@code null} if not present
     */
    default <T> T queryParameter(String parameterName) {
        return (T) queryParameters().get(parameterName);
    }

    /**
     * Default implementation of {@code CommandRequest}, encapsulating a {@link CommandRoute}
     * and its associated query parameters.
     *
     * @param route           the {@link CommandRoute} for the command
     * @param queryParameters the query parameters for the command
     */
    record CommandRequestDefaultImplementation(CommandRoute route, Map<String, Object> queryParameters, Context context) implements CommandRequest {

    }
}
