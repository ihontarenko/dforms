package df.base.operation;

import df.base.common.operation.Operation;
import df.base.common.operation.OperationContext;
import df.base.common.operation.annotation.OperationCommand;

import java.util.Map;

@OperationCommand(operation = "validation", command = "not_null")
public class TestOperation implements Operation {
    @Override
    public Object execute(Map<String, Object> parameters, OperationContext context) {
        return null;
    }
}
