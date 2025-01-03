package df.common.parser.evaluation;

import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.ast.node.RootNode;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class AnyCompiler implements Compiler<RootNode, Object> {

    @Override
    public Object compile(RootNode node, EvaluationContext evaluationContext) {
        return stream(node.children()).map(n -> n.evaluate(evaluationContext)).collect(toList());
    }

    @Override
    public Class<? extends RootNode> nodeType() {
        return RootNode.class;
    }

}