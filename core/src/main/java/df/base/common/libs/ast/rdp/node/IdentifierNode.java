package df.base.common.libs.ast.rdp.node;

import df.base.common.libs.ast.rdp.context.InterpreterContext;

public class IdentifierNode extends AbstractEvaluableNode {

    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    @Override
    public Object evaluate(InterpreterContext ctx) {
        return ctx.getVariable(name);
    }

}
