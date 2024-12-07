package df.base.common.operation;

public class OperationDefinition {

    private final String operation;
    private final String command;
    private       String parameters;

    public OperationDefinition(String operation, String command, String parameters) {
        this.operation = operation;
        this.command = command;
        this.parameters = parameters;
    }

    public String getOperation() {
        return operation;
    }

    public String getCommand() {
        return command;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "DEFINITION [OPERATION '%s/%s'; RAW_PARAMETERS '%s']".formatted(operation, command, parameters);
    }
}
