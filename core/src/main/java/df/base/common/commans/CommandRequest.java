package df.base.common.commans;

import java.util.Map;

public interface CommandRequest {

    static CommandRequest create(CommandRoute route, Map<String, Object> queryParameters) {
        return new SimpleOperationRequest(route, queryParameters);
    }

    CommandRoute route();

    Map<String, Object> queryParameters();

    default Object parameter(String parameterName) {
        return queryParameters().get(parameterName);
    }

    record SimpleOperationRequest(CommandRoute route, Map<String, Object> queryParameters) implements CommandRequest {

    }

}
