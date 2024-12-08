package df.base.common.operation;

import java.util.Map;

public interface OperationRequest {

    static OperationRequest create(String operation, String action, Map<String, Object> parameters) {
        return new SimpleOperationRequest(operation, action, parameters);
    }

    String operation();

    String action();

    Map<String, Object> parameters();

    default Object parameter(String parameterName) {
        return parameters().get(parameterName);
    }

    record SimpleOperationRequest(String operation, String action,
                                  Map<String, Object> parameters) implements OperationRequest {

    }

}
