package df.base.common.operation;

public class OperationDefinition {

    private final String operation;
    private final String actionName;
    private       String parameters;

    public OperationDefinition(String operation, String actionName, String parameters) {
        this.operation = operation;
        this.actionName = actionName;
        this.parameters = parameters;
    }

    public String getOperation() {
        return operation;
    }

    public String getActionName() {
        return actionName;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "DEFINITION [OPERATION '%s/%s'; RAW_PARAMETERS '%s']".formatted(operation, actionName, parameters);
    }
}
