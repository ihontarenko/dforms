package df.base.common.commans;

import df.base.PackageCoreRoot;
import df.base.common.commans.annotation.Action;
import df.base.common.commans.annotation.Command;
import df.base.common.invocable.Invocable;
import df.base.common.reflection.MethodFilter;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static df.base.common.reflection.ClassFinder.findAnnotatedClasses;
import static df.base.common.reflection.MethodFinder.FINDER;
import static df.base.common.reflection.Reflections.findFirstConstructor;
import static df.base.common.reflection.Reflections.instantiate;

/**
 * {@code CommandsManagerFactory} provides factory methods for creating and configuring {@link CommandsManager} instances.
 * This class facilitates the registration of commands and actions based on annotated classes and methods.
 */
public class CommandsManagerFactory {

    /**
     * Creates a new {@link CommandsManager} with no pre-configured commands or actions.
     *
     * @return a new {@link CommandsManager} instance
     */
    public static CommandsManager createDefault() {
        return new CommandsManager();
    }

    /**
     * Creates a fully configured {@link CommandsManager} by scanning annotated classes and methods.
     *
     * <p>Classes annotated with {@link Command} are scanned within the {@link PackageCoreRoot} package. Methods
     * annotated with {@link Action} are then registered as actions for the respective command.</p>
     *
     * @return a configured {@link CommandsManager} instance
     */
    public static CommandsManager create() {
        return create(manager -> {
            for (Class<?> annotatedClass : findAnnotatedClasses(Command.class, PackageCoreRoot.class)) {
                Command      commandAnnotation = annotatedClass.getAnnotation(Command.class);
                MethodFilter filter            = FINDER.filter(annotatedClass);
                Object       commandInstance   = instantiate(findFirstConstructor(annotatedClass));

                // build filter to find applicable method by signature and specific annotation
                filter.parameterTypes(String.class, CommandRequest.class).annotated(Action.class);

                for (Method method : filter.find()) {
                    Action    actionAnnotation = method.getAnnotation(Action.class);
                    Invocable invocable        = new HandlerExecutor(commandInstance, method);

                    // bind the same invocable object to each of registered action
                    for (String action : actionAnnotation.value()) {
                        manager.register(commandAnnotation.value(), action, invocable);
                    }
                }
            }
        });
    }

    /**
     * Creates a {@link CommandsManager} with custom configuration using the provided {@link Consumer}.
     *
     * @param configurator a {@link Consumer} that accepts a {@link CommandsManager} and performs custom configuration
     * @return a customized {@link CommandsManager} instance
     */
    public static CommandsManager create(Consumer<CommandsManager> configurator) {
        CommandsManager commandsManager = createDefault();
        configurator.accept(commandsManager);
        return commandsManager;
    }
}
