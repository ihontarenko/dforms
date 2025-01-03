package df.common.parser.evaluation;

import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.parser.ast.IdentifierNode;

public class IdentifierCompiler implements Compiler<IdentifierNode, String> {

    @Override
    public String compile(IdentifierNode node, EvaluationContext evaluationContext) {
        return node.getIdentifier();
    }

    @Override
    public Class<? extends IdentifierNode> nodeType() {
        return IdentifierNode.class;
    }

}
