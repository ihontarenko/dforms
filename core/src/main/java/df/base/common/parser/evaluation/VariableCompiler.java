package df.base.common.parser.evaluation;

import df.base.common.ast.compiler.Compiler;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.VariableNode;

public class VariableCompiler implements Compiler<VariableNode> {

    @Override
    public Object compile(VariableNode node, EvaluationContext ctx) {
        return ctx.requireVariable(node.getVariableName());
    }

    @Override
    public Class<? extends VariableNode> nodeType() {
        return VariableNode.class;
    }

}
