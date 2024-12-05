package df.base.common.libs.ast.compiler;

import df.base.common.libs.ast.node.Node;

public interface Compiler<N extends Node> {

    Object compile(N node, EvaluationContext ctx);

    Class<? extends N> nodeType();

}
