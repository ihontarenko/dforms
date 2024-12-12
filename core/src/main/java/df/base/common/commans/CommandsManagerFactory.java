package df.base.common.commans;

import df.base.PackageCoreRoot;
import df.base.common.matcher.reflection.TypeMatchers;
import df.base.common.commans.annotation.Command;
import df.base.common.reflection.ClassFinder;

import java.util.function.Consumer;

import static df.base.common.reflection.ClassFinder.findAnnotatedClasses;
import static df.base.common.reflection.Reflections.findFirstConstructor;
import static df.base.common.reflection.Reflections.instantiate;

public class CommandsManagerFactory {

    /**
     * Creates a default {@link CommandsManager} instance without any predefined operators.
     *
     * @return a new, empty {@link CommandsManager} instance.
     */
    public static CommandsManager createDefault() {
        return new CommandsManager();
    }

    /**
     * Creates an {@link CommandsManager} instance with operators registered via annotations.
     *
     * <p>This method scans classes for a specific annotation (@OperationCommand) and registers operators in the {@link CommandsManager}.
     * It ensures that only classes that implement the {@link ActionHandler} interface are registered.</p>
     *
     * <p>The classes are found using {@link ClassFinder} and are checked to match the {@link ActionHandler} type using {@link TypeMatchers}.</p>
     *
     * @return a new {@link CommandsManager} instance with annotated operators.
     */
    public static CommandsManager create() {
        return create(manager -> {
            for (Class<?> annotatedClass : findAnnotatedClasses(Command.class, PackageCoreRoot.class)) {
                if (TypeMatchers.isSupertype(ActionHandler.class).matches(annotatedClass)) {
                    Command          annotation = annotatedClass.getAnnotation(Command.class);
                    ActionHandler<?> operator   = (ActionHandler<?>) instantiate(findFirstConstructor(annotatedClass));
//                    for (String action : annotation.actions()) {
//                        manager.register(annotation.operation(), action, operator);
//                    }
                } else {
                    throw new IllegalCommandClassImplementationException(
                            "Annotated class-operator '%s' is required to implement the interface '%s'"
                                    .formatted(annotatedClass, ActionHandler.class));
                }
            }
        });
    }

    /**
     * Creates a custom-configured {@link CommandsManager} instance using a provided configurator function.
     *
     * <p>The configurator function takes a default {@link CommandsManager} instance as input,
     * applies custom configurations</p>
     *
     * @param configurator a function that configures an {@link CommandsManager} instance.
     * @return a configured {@link CommandsManager} instance.
     */
    public static CommandsManager create(Consumer<CommandsManager> configurator) {
        CommandsManager operationManager = createDefault();

        configurator.accept(operationManager);

        return operationManager;
    }
}
