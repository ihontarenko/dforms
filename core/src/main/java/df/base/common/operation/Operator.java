package df.base.common.operation;

public interface Operator<T> {
    T handle(OperationRequest request);
}
