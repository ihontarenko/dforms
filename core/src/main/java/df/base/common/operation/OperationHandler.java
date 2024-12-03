package df.base.common.operation;

public interface OperationHandler<T extends Operation> {

    T getOperation(String operationName);

    void addOperation(String name, T operation);

    String getName();

}
