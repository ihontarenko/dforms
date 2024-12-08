package df.base.common.operation;

import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.operation.compiler.HandlerDefinitionCompiler;
import df.base.common.parser.ParserService;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link OperationManager} class is responsible for registering, managing, and executing operations
 * with specific actions and parameters. It provides methods for parsing input definitions, validating syntax,
 * and executing operators dynamically based on the parsed input.
 *
 * <p>OperationManager manages a collection of operators, each associated with an operation and action.
 * It parses raw parameters, validates them, and delegates the execution to the appropriate operator.</p>
 *
 * <p>Operations are identified by a definition string in the format of "operation:action//parameters".
 * These definitions are parsed, and the corresponding operator is executed with the parsed parameters.</p>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * OperationManager manager = new OperationManager();
 *
 * // Execute a validation operation with specific parameters
 * System.out.println(manager.execute("#validation:empty_string//(message='value must be non-null')", "test").toString());
 *
 * // Execute a not_null validation with a condition and a minimum value for a string
 * manager.execute("#validation:not_null", "(value=#root.startsWith('t'), min=10)", "test");
 *
 * // Execute a range validation with conditions for the string "test"
 * manager.execute("validation", "range", "(value=#root.startsWith('t'), min=10)", "test");
 * }</pre>
 */
public class OperationManager {

    /**
     * Singleton instance of the OperationManager.
     */
    public final static OperationManager INSTANCE = OperationManagerFactory.createWithAnnotatedOperator();

    /**
     * A map that holds operators indexed by operation and action names.
     */
    private final Map<String, Map<String, Operator<?>>> operators = new HashMap<>();

    /**
     * A service for parsing and evaluating definitions and parameters.
     */
    private final ParserService parserService = new ParserService();

    /**
     * Constructs an instance of the OperationManager and initializes the necessary parsers and compilers.
     */
    public OperationManager() {
        parserService.getEvaluationContext().addCompiler(new HandlerDefinitionCompiler());
    }

    /**
     * Registers an operator for a specific operation and action.
     *
     * @param operation The name of the operation.
     * @param action The name of the action associated with the operation.
     * @param operator The operator to handle the action for the operation.
     */
    public void register(String operation, String action, Operator<?> operator) {
        operators.computeIfAbsent(operation, k -> new HashMap<>()).put(action, operator);
    }

    /**
     * Executes an operation based on a definition string and a target object.
     * The definition is split into an operation and action part by the "//" delimiter.
     *
     * @param definition A string representing the operation and action in the format "operation//action".
     * @param target The target object on which the operation will be executed.
     * @param <T> The return type of the operation.
     * @return The result of executing the operator's handle method.
     * @throws OperationManagerException If the definition format is incorrect or any errors occur during execution.
     */
    public <T> T execute(String definition, Object target) {
        String[] parts = definition.split("//");

        if (parts.length != 2) {
            throw new OperationManagerException("Wrong definition format [%s].".formatted(definition));
        }

        return execute(parts[0], parts[1], target);
    }

    /**
     * Executes an operation based on a definition string, raw parameters, and a target object.
     * The definition is parsed to extract the operation and action details, and the raw parameters
     * are parsed and validated before execution.
     *
     * @param definition A string representing the operation definition.
     * @param rawParameters A string representing the raw parameters to be parsed and passed to the operation.
     * @param target The target object on which the operation will be executed.
     * @param <T> The return type of the operation.
     * @return The result of executing the operator's handle method.
     * @throws OperationManagerException If there are errors during parsing or execution.
     */
    public <T> T execute(String definition, String rawParameters, Object target) {
        Node rootNode = parserService.parse(definition);
        EvaluationContext context = parserService.getEvaluationContext();
        OperationDefinition operationDefinition = (OperationDefinition) rootNode.evaluate(context);

        return execute(operationDefinition.getOperation(), operationDefinition.getActionName(), rawParameters, target);
    }

    /**
     * Executes an operation by parsing the raw parameters, validating the syntax, and delegating
     * to the appropriate operator for the specified operation and action.
     *
     * @param operation The name of the operation to execute.
     * @param action The name of the action to execute within the operation.
     * @param rawParameters A string containing the raw parameters to be parsed and validated.
     * @param target The target object on which the operation will be executed.
     * @param <T> The return type of the operation.
     * @return The result of the operator's handle method after execution.
     * @throws OperationManagerException If parameter parsing fails or no operator is found for the given operation and action.
     */
    public <T> T execute(String operation, String action, String rawParameters, Object target) {
        EvaluationContext evaluationContext = parserService.getEvaluationContext();
        evaluationContext.setVariable("root", target);

        Node rootNode = parserService.parse(rawParameters);
        Map<String, Object> parsedParameters;

        if (rootNode.evaluate(evaluationContext) instanceof Map<?, ?> map) {
            parsedParameters = (Map<String, Object>) map;
        } else {
            throw new OperationManagerException(
                    "Failed to parse the provided parameters [%s]. Please review the syntax of the provided parameters."
                            .formatted(rawParameters));
        }

        return execute(OperationRequest.create(operation, action, parsedParameters));
    }

    /**
     * Executes an operation request by finding the appropriate operator and invoking its handle method.
     *
     * @param request The operation request containing the operation, action, and parameters.
     * @param <T> The return type of the operation.
     * @return The result of executing the operator's handle method.
     * @throws OperationManagerException If no operator is found for the requested operation or action.
     */
    @SuppressWarnings("unchecked")
    public <T> T execute(OperationRequest request) {
        Map<String, Operator<?>> actions = operators.get(request.operation());

        if (actions == null) {
            throw new OperationManagerException(
                    "No actions are registered for the operation '%s'."
                            .formatted(request.operation()));
        }

        Operator<T> operator = (Operator<T>) actions.get(request.action());

        if (operator == null) {
            throw new OperationManagerException(
                    "No action is registered for the name '%s' under the operation '%s'."
                            .formatted(request.action(), request.operation()));
        }

        return operator.handle(request);
    }
}
