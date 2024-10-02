package df.base.common.invocable;

import java.lang.reflect.Method;

public class ObjectMethod extends AbstractInvocable {

    private final Object targetObject;

    public ObjectMethod(Object targetObject, String methodName, Class<?>... parameterTypes) {
        super(targetObject.getClass(), methodName, parameterTypes);
        this.targetObject = targetObject;
    }

    public ObjectMethod(Object targetObject, Method method) {
        super(method);
        this.targetObject = targetObject;
    }

    @Override
    public Object getTarget() {
        return this.targetObject;
    }

    @Override
    public InvokeResult invoke() {
        validateParameters();
        return Invoker.invoke(this);
    }

}
