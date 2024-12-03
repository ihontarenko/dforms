package df.base.common.libs.ast.compiler;

import df.base.common.libs.ast.node.Node;

public interface Compiler {
    Object compile(Node node, CompilerContext ctx);
}
