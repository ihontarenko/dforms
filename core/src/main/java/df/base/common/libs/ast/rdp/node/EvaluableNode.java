package df.base.common.libs.ast.rdp.node;

import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.rdp.context.InterpreterContext;

public interface EvaluableNode extends Node {
    Object evaluate(InterpreterContext ctx);
}
