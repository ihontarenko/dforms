package df.base.common.parser.ast;

import df.base.common.ast.node.EntryNode;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.ast.token.Token;

import static df.base.common.ast.token.DefaultToken.*;
import static java.lang.Integer.parseInt;

public class LiteralNode extends EntryNode {

    private Object value;

    public LiteralNode(Token.Entry entry) {
        super(entry);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        return ctx.getCompiler(this).compile(this, ctx);
    }

}
