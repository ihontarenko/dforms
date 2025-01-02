package df.base.common.parser.evaluation;

import df.base.common.ast.compiler.Compiler;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.ValuesNode;

public class ValuesCompiler implements Compiler<ValuesNode> {

    @Override
    public Object compile(ValuesNode node, EvaluationContext ctx) {
        return node.getElements().stream().map(n -> n.evaluate(ctx)).toList();
    }

    @Override
    public Class<? extends ValuesNode> nodeType() {
        return ValuesNode.class;
    }

}
