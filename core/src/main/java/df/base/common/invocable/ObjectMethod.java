package df.base.common.invocable;

public class ObjectMethod extends AbstractInvocable {

    public ObjectMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        super(clazz, methodName, parameterTypes);
    }

    @Override
    public Object invoke() {
        return null;
    }

}
