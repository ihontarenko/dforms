package df.base.common.operation;

import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.operation.compiler.HandlerDefinitionCompiler;
import df.base.common.parser.ParserService;

import java.util.HashMap;
import java.util.Map;

public class OperationManager {

    private final Map<String, Map<String, Operator<?>>> operators     = new HashMap<>();
    private final ParserService                         parserService = new ParserService();

    public OperationManager() {
        parserService.getEvaluationContext().addCompiler(new HandlerDefinitionCompiler());
    }

    public void register(String operation, String action, Operator<?> operator) {
        operators.computeIfAbsent(operation, k -> new HashMap<>()).put(action, operator);
    }

    public <T> T execute(String definition, Object target) {
        String[] parts   = definition.split("//");

        if (parts.length != 2) {
            throw new OperationManagerException("Wrong definition format [%s].".formatted(definition));
        }

        return execute(parts[0], parts[1], target);
    }

    public <T> T execute(String definition, String rawParameters, Object target) {
        Node                rootNode            = parserService.parse(definition);
        EvaluationContext   context             = parserService.getEvaluationContext();
        OperationDefinition operationDefinition = (OperationDefinition) rootNode.evaluate(context);

        return execute(operationDefinition.getOperation(), operationDefinition.getActionName(), rawParameters, target);
    }

    public <T> T execute(String operation, String action, String rawParameters, Object target) {
        EvaluationContext evaluationContext = parserService.getEvaluationContext();
        evaluationContext.setVariable("root", target);

        Node                rootNode = parserService.parse(rawParameters);
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
                            .formatted(request.action(), request.action()));
        }

        return operator.handle(request);
    }
}
