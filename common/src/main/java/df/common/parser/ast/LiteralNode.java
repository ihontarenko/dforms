package df.common.parser.ast;

import df.common.ast.node.EntryNode;
import df.common.ast.compiler.EvaluationContext;
import df.common.ast.token.Token;

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
