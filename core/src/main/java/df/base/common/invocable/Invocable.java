package df.base.common.invocable;

import java.util.Collection;

public interface Invocable {

    InvokeResult invoke();

    void addParameter(MethodParameter parameter);

    default void addParameters(Collection<?> parameters) {
        int index = getParameters().size();
        for (Object parameter : parameters) {
            addParameter(new MethodParameter(index++, parameter));
        }
    }

    Collection<? extends MethodParameter> getParameters();

    Object[] getPreparedParameters();

    MethodDescriptor getDescriptor();

    default Object getTarget() {
        return null;
    }

}
