package df.base.common.commans;

/**
 * {@code CommandRoute} represents the route for a command, including the command name and its associated action.
 *
 * <p>This record provides methods to access the command and action names, as well as a method to generate
 * a string representation of the route in the format "command/action".</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CommandRoute route = new CommandRoute("myCommand", "myAction");
 * String command = route.command(); // "myCommand"
 * String action = route.action();   // "myAction"
 * String routeString = route.string(); // "myCommand/myAction"
 * }</pre>
 *
 * @param command the name of the command
 * @param action  the name of the associated action
 */
public record CommandRoute(String command, String action) {

    /**
     * Generates a string representation of the command route in the format "command/action".
     *
     * @return a string representation of the command route
     */
    public String string() {
        return "%s/%s".formatted(command, action);
    }
}
