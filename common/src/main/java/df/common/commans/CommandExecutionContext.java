package df.common.commans;

import svit.support.context.AbstractContext;

import java.util.Map;
import java.util.function.Consumer;

/**
 * {@code CommandExecutionContext} is a specialized implementation of {@link AbstractContext}.
 * It provides utility methods to create and configure a context for command execution scenarios.
 * This class is immutable and cannot be subclassed.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CommandExecutionContext context = CommandExecutionContext.create(ctx -> {
 *     ctx.setProperty("key1", "value1");
 *     ctx.setProperty("key2", 42);
 * });
 *
 * CommandExecutionContext singlePropertyContext = CommandExecutionContext.create("key", "value");
 *
 * Map<String, Object> properties = Map.of("key1", "value1", "key2", 42);
 * CommandExecutionContext multiPropertyContext = CommandExecutionContext.create(properties);
 * }</pre>
 */
final public class CommandExecutionContext extends AbstractContext {

    /**
     * Creates a new instance of {@code CommandExecutionContext} and applies the given configurator.
     *
     * @param configurator a {@link Consumer} that configures the newly created context
     * @return a configured {@code CommandExecutionContext} instance
     */
    public static CommandExecutionContext create(Consumer<CommandExecutionContext> configurator) {
        CommandExecutionContext context = new CommandExecutionContext();

        configurator.accept(context);

        return context;
    }

    /**
     * Creates a new instance of {@code CommandExecutionContext} and sets a single property.
     *
     * @param key   the key for the property
     * @param value the value for the property
     * @return a configured {@code CommandExecutionContext} instance
     */
    public static CommandExecutionContext create(String key, Object value) {
        return create(ctx -> ctx.setProperty(key, value));
    }

    /**
     * Creates a new instance of {@code CommandExecutionContext} and sets multiple properties.
     *
     * @param values a map of properties to set in the context
     * @return a configured {@code CommandExecutionContext} instance
     */
    public static CommandExecutionContext create(Map<String, ?> values) {
        return create(ctx -> values.forEach(ctx::setProperty));
    }
}