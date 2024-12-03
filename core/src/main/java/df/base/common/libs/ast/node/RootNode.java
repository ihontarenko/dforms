package df.base.common.libs.ast.node;

import df.base.common.libs.ast.compiler.EvaluationContext;

import java.util.stream.Collectors;

public class RootNode extends EntryNode {

    public RootNode() {
        super(null);
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        return children.stream().map(node -> node.evaluate(ctx))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ROOT-NODE";
    }

}
