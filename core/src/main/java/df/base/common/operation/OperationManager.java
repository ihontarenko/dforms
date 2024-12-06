package df.base.common.operation;

import df.base.common.libs.ast.node.Node;
import df.base.common.parser.ParameterParser;

import java.util.List;
import java.util.Map;

final public class OperationManager {

    private final OperationHandlerRegistry handlerFactory;
    private final ParameterParser          parser;

    public OperationManager() {
        this.handlerFactory = new OperationHandlerRegistry();
        this.parser = new ParameterParser();
    }

    public OperationHandlerRegistry getHandlerFactory() {
        return handlerFactory;
    }

    public ParameterParser getParser() {
        return parser;
    }

    public Object executeOperation(String operation, String parameters) {
        String[]            handlerParts      = operation.substring(1).split(":");
        String              handlerName       = handlerParts[0];
        String              operationName     = handlerParts[1];
        Node                rootNode          = parser.parse(parameters);
        OperationContext    operationContext  = new OperationContext();
        Map<String, Object> result            = null;

        if (rootNode.evaluate(parser.getEvaluationContext()) instanceof List<?> objects) {
            if (objects.size() == 1 && objects.get(0) instanceof Map<?, ?> map) {
                result = (Map<String, Object>) map;
            }
        }

        return handlerFactory.getOperationHandler(handlerName).getOperation(operationName)
                .execute(result, operationContext);
    }

    public void registerOperationHandler() {

    }

}
