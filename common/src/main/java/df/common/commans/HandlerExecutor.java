package df.common.commans;

import org.jmouse.common.support.invocable.InvokeResult;
import org.jmouse.common.support.invocable.ObjectMethodInvocable;

import java.lang.reflect.Method;

final public class HandlerExecutor extends ObjectMethodInvocable {

    public HandlerExecutor(Object targetObject, Method method) {
        super(targetObject, method);
    }

    @Override
    public InvokeResult invoke() {
        return super.invoke();
    }

}
