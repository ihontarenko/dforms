package df.base.common.context;

import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

abstract public class AbstractArgumentsContext implements ArgumentsContext {

    private final Map<Object, Object> arguments = new HashMap<>();

    @Override
    public <T> T requireArgument(Object name) {
        if (hasArgument(name)) {
            return getArgument(name);
        }

        throw new ArgumentNotFoundException("Required argument not found [%s]".formatted(name));
    }

    @Override
    public <T> T getArgument(Object name) {
        return (T) arguments.getOrDefault(name, new Object());
    }

    @Override
    public void setArgument(Object name, Object argument) {
        arguments.put(name, argument);
    }

    @Override
    public void setArgument(Object argument) {
        setArgument(ClassUtils.getUserClass(requireNonNull(argument).getClass()), argument);
    }

    @Override
    public void setArguments(Object... arguments) {
        for (Object argument : arguments) {
            setArgument(argument);
        }
    }

    @Override
    public boolean hasArgument(Object name) {
        return arguments.containsKey(name);
    }

    @Override
    public String toString() {
        return "ARGUMENTS: %s".formatted(arguments);
    }

}
