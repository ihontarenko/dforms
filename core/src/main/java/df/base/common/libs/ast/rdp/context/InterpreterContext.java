package df.base.common.libs.ast.rdp.context;

import df.base.common.libs.ast.rdp.AbstractContext;

public class InterpreterContext extends AbstractContext {

    public Object getVariable(String name) {
        return requireProperty(name);
    }

    public void setVariable(String name, Object value) {
        setProperty(name, value);
    }

}
