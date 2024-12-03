package df.base.common.parser.compiler;

import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.CompilerContext;
import df.base.common.libs.ast.node.Node;

public class AnyCompiler implements Compiler {

    @Override
    public Object compile(Node node, CompilerContext ctx) {
        return null;
    }

}
