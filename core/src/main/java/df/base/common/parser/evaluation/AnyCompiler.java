package df.base.common.parser.evaluation;

import df.base.common.ast.compiler.Compiler;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.ast.node.RootNode;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class AnyCompiler implements Compiler<RootNode> {

    @Override
    public Object compile(RootNode node, EvaluationContext ctx) {
        return stream(node.children()).map(n -> n.evaluate(ctx)).collect(toList());
    }

    @Override
    public Class<? extends RootNode> nodeType() {
        return RootNode.class;
    }

}
