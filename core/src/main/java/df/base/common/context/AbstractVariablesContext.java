package df.base.common.context;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractVariablesContext extends AbstractContext implements VariablesContext {

    private final Map<Object, Object> variables = new HashMap<>();

    @Override
    public <T> T getVariable(Object name) {
        return (T) variables.get(name);
    }

    @Override
    public void setVariable(Object name, Object value) {
        variables.put(name, value);
    }

    @Override
    public String toString() {
        return "VARIABLES: %s".formatted(variables);
    }

}
