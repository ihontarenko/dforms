package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.token.Token;

public class VariableNode extends EntryNode {

    public VariableNode(Token.Entry entry) {
        super(entry);
    }

    @Override
    public Object evaluate(EvaluationContext evaluationContext) {
        return evaluationContext.requireVariable(entry.value().substring(1));
    }

}
