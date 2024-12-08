package df.base.common.operation;

import df.base.PackageCoreRoot;
import df.base.common.matcher.reflection.TypeMatchers;
import df.base.common.operation.annotation.Operation;
import df.base.common.reflection.ClassFinder;
import df.base.common.reflection.Reflections;

import java.util.Set;
import java.util.function.Consumer;

import static df.base.common.reflection.ClassFinder.findAnnotatedClasses;
import static df.base.common.reflection.Reflections.findFirstConstructor;
import static df.base.common.reflection.Reflections.instantiate;

/**
 * Factory for creating instances of {@link OperationManager} with predefined configurations or custom setups.
 *
 * <p>This factory simplifies the creation and initialization of {@link OperationManager} objects,
 * offering multiple ways to configure them, including default, annotated, or custom configurations.</p>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * // Create a default OperationManager instance
 * OperationManager defaultManager = OperationManagerFactory.createDefault();
 *
 * // Create an OperationManager with custom configuration
 * OperationManager customManager = OperationManagerFactory.create(manager -> {
 *     manager.register("custom_operation", "custom_action", action -> "Custom Operator Executed");
 * });
 *
 * // Example: Annotated operator registration (requires implementation)
 * OperationManager annotatedManager = OperationManagerFactory.createWithAnnotatedOperator();
 * }</pre>
 */
public class OperationManagerFactory {

    /**
     * Creates a default {@link OperationManager} instance without any predefined operators.
     *
     * @return a new, empty {@link OperationManager} instance.
     */
    public static OperationManager createDefault() {
        return new OperationManager();
    }

    /**
     * Creates an {@link OperationManager} instance with operators registered via annotations.
     *
     * <p>This method scans classes for a specific annotation (@OperationCommand) and registers operators in the {@link OperationManager}.
     * It ensures that only classes that implement the {@link Operator} interface are registered.</p>
     *
     * <p>The classes are found using {@link ClassFinder} and are checked to match the {@link Operator} type using {@link TypeMatchers}.</p>
     *
     * @return a new {@link OperationManager} instance with annotated operators.
     */
    public static OperationManager createWithAnnotatedOperator() {
        OperationManager operationManager = new OperationManager();

        // Scan for classes with the @OperationCommand annotation
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(Operation.class, PackageCoreRoot.class);

        for (Class<?> annotatedClass : annotatedClasses) {
            // Check if the class implements the Operator interface
            if (TypeMatchers.isSupertype(Operator.class).matches(annotatedClass)) {
                // Retrieve the @OperationCommand annotation
                Operation annotation = annotatedClass.getAnnotation(Operation.class);

                // Instantiate the operator class and register it in the OperationManager
                Operator<?> operator = (Operator<?>) instantiate(findFirstConstructor(annotatedClass));

                for (Operation.Action action : annotation.actions()) {
                    // Register the operator with its corresponding operation and action
                    operationManager.register(annotation.operation(), action.value(), operator);
                }
            } else {
                // throw exception if operation dont implement Operator interface
                throw new BadOperationDefinitionException("Annotated class '%s' should implement '%s' interface."
                        .formatted(annotatedClass, Operator.class));
            }
        }

        return operationManager;
    }

    /**
     * Creates a custom-configured {@link OperationManager} instance using a provided configurator function.
     *
     * <p>The configurator function takes a default {@link OperationManager} instance as input,
     * applies custom configurations</p>
     *
     * @param configurator a function that configures an {@link OperationManager} instance.
     * @return a configured {@link OperationManager} instance.
     */
    public static OperationManager create(Consumer<OperationManager> configurator) {
        OperationManager operationManager = createDefault();

        configurator.accept(operationManager);

        return operationManager;
    }
}
