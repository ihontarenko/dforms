package df.common.commans;

import svit.invocable.InvokeResult;
import svit.invocable.ObjectMethodInvocable;

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
