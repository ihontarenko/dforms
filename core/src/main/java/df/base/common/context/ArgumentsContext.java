package df.base.common.context;

import org.springframework.util.ClassUtils;

import static java.util.Objects.requireNonNull;

public interface ArgumentsContext {

    default  <T> T requireArgument(Object name) {
        if (hasArgument(name)) {
            return getArgument(name);
        }

        throw new ArgumentNotFoundException("Required argument not found [%s]".formatted(name));
    }


    <T> T getArgument(Object name);

    void setArgument(Object name, Object argument);

    default void setArgument(Object argument) {
        setArgument(ClassUtils.getUserClass(requireNonNull(argument).getClass()), argument);
    }

    default void setArguments(Object... arguments) {
        for (Object argument : arguments) {
            setArgument(argument);
        }
    }

    boolean hasArgument(Object name);

}
