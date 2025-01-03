package df.common.parser.evaluation;

import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.parser.ast.ValuesNode;

import java.util.List;

public class ValuesCompiler implements Compiler<ValuesNode, List<Object>> {

    @Override
    public List<Object> compile(ValuesNode node, EvaluationContext evaluationContext) {
        return node.getElements().stream().map(n -> n.evaluate(evaluationContext)).toList();
    }

    @Override
    public Class<? extends ValuesNode> nodeType() {
        return ValuesNode.class;
    }

}
