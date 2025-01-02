package df.base.common.parser.evaluation;

import df.base.common.ast.compiler.Compiler;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.IdentifierNode;

public class IdentifierCompiler implements Compiler<IdentifierNode> {

    @Override
    public Object compile(IdentifierNode node, EvaluationContext ctx) {
        return node.getIdentifier();
    }

    @Override
    public Class<? extends IdentifierNode> nodeType() {
        return IdentifierNode.class;
    }

}
