package df.base.operation;

import df.base.common.operation.OperationRequest;
import df.base.common.operation.Operator;
import df.base.common.operation.annotation.Operation;

@Operation(operation = "validation", actions = {
        @Operation.Action("test"), @Operation.Action("test2")
})
public class TestOperation implements Operator<Object> {

    @Override
    public Object handle(OperationRequest request) {
        System.out.println("REQUEST: " + request);
        return request.parameter("value");
    }

}
