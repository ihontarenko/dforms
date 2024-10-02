package df.base.common.invocable;

import df.base.common.context.ErrorDetails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final public class Invoker {

    public static InvokeResult invoke(Invocable invocable) {
        InvokeResult result = new InvokeResult();

        try {
            Method method = invocable.getDescriptor().getNativeMethod();
            result.setReturnValue(method.invoke(invocable.getTarget(), invocable.getPreparedParameters()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            result.addError(new ErrorDetails(null, e.getMessage()));
        }

        return result;
    }

}
