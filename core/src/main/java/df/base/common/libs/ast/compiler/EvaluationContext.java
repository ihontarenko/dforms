package df.base.common.libs.ast.compiler;

import df.base.common.context.AbstractContext;
import df.base.common.libs.ast.node.EvaluationContextException;

import java.lang.reflect.Method;

public class EvaluationContext extends AbstractContext {

    public Method getFunction(String name) {
        Method function = getProperty(name);

        if (function == null) {
            throw new EvaluationContextException(
                    "Evaluation context does not contain the required function '%s'.".formatted(name));
        }

        return function;
    }

    public void setFunction(String name, Method method) {
        setProperty(name, method);
    }

    public void setFunction(Method method) {
        setProperty(method.getName(), method);
    }

    public Object requireVariable(String name) {
        Object variable = getVariable(name);

        if (variable == null) {
            throw new EvaluationContextException(
                    "Evaluation context does not contain the required variable '%s'.".formatted(name));
        }

        return variable;
    }

    public Object getVariable(String name) {
        return getProperty(name);
    }

    public void setVariable(String name, Object value) {
        setProperty(name, value);
    }

}
