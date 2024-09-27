package df.base.common.libs.ast.node;

import df.base.common.context.AbstractContext;

public class EvaluationContext extends AbstractContext {

    public Object getVariable(String name) {
        return getProperty(name);
    }

    public void setVariable(String name, Object value) {
        setProperty(name, value);
    }

}
