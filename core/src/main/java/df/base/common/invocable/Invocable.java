package df.base.common.invocable;

public interface Invocable {

    Object invoke();

    void addParameter(MethodParameter parameter);

    Object[] getPreparedParameters();

    MethodDescriptor getDescriptor();

    default Object getTarget() {
        return null;
    }

}
