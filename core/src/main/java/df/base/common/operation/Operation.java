package df.base.common.operation;

import java.util.Map;

@FunctionalInterface
public interface Operation {
    Object execute(Map<String, Object> parameters, OperationContext context);
}
