package df.base.common.parser.evaluation;

import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.VariableNode;

public class VariableCompiler implements Compiler<VariableNode> {

    @Override
    public Object compile(VariableNode node, EvaluationContext ctx) {
        return ctx.requireVariable(node.entry().value().substring(1));
    }

    @Override
    public Class<? extends VariableNode> nodeType() {
        return VariableNode.class;
    }

}
