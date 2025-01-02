package df.base.common.ast.compiler;

import df.base.common.ast.node.Node;

public interface Compiler<N extends Node> {

    Object compile(N node, EvaluationContext ctx);

    Class<? extends N> nodeType();

}
