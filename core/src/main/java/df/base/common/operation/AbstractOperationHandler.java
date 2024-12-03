package df.base.common.operation;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractOperationHandler<T extends Operation> implements OperationHandler<T> {

    protected Map<String, T> operations = new HashMap<>();

    @Override
    public void addOperation(String name, T operation) {
        operations.put(name, operation);
    }

    @Override
    public T getOperation(String operationName) {
        T operation = operations.get(operationName);

        if (operation == null) {
            throw new IllegalArgumentException("UNKNOWN OPERATION COMMAND: %s".formatted(operationName));
        }

        return operation;
    }

}
