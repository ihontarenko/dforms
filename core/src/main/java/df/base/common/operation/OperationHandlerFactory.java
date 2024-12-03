package df.base.common.operation;

import java.util.HashMap;
import java.util.Map;

public class OperationHandlerFactory {

    private final Map<String, OperationHandler<?>> handlers = new HashMap<>();

    public <T extends OperationHandler<?>> T getOperationHandler(String name) {
        OperationHandler<?> handler = handlers.get(name);

        if (handler == null) {
            throw new IllegalArgumentException("UNKNOWN OPERATION HANDLER TYPE: %s".formatted(name));
        }

        return (T) handler;
    }

    public void addOperationHandler(OperationHandler<?> handler) {
        handlers.put(handler.getName(), handler);
    }

}
