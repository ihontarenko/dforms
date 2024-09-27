package df.base.common.libs.ast.node.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.token.Token;

public class VariableNode extends EntryNode {

    public VariableNode(Token.Entry entry) {
        super(entry);
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        return ctx.requireVariable(entry.value().substring(1));
    }

}
