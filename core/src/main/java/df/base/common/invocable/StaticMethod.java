package df.base.common.invocable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class StaticMethod extends AbstractInvocable {

    public StaticMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        super(clazz, methodName, parameterTypes);
    }

    public StaticMethod(Method method) {
        super(method);
    }

    @Override
    public InvokeResult invoke() {
        if ((descriptor.getNativeMethod().getModifiers() & Modifier.STATIC) == 0) {
            throw new InvocationException("The method '%s' is not static and cannot be invoked as such."
                    .formatted(descriptor.getName()));
        }

        validateParameters();

        return Invoker.invoke(this);
    }

}

