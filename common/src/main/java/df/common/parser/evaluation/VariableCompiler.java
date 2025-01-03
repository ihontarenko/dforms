package df.common.parser.evaluation;

import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.parser.ast.VariableNode;

public class VariableCompiler implements Compiler<VariableNode, Object> {

    @Override
    public Object compile(VariableNode node, EvaluationContext evaluationContext) {
        return evaluationContext.requireVariable(node.getVariableName());
    }

    @Override
    public Class<? extends VariableNode> nodeType() {
        return VariableNode.class;
    }

}
