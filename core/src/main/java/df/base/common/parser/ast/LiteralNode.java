package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.libs.ast.token.Token;

import static df.base.common.libs.ast.token.DefaultToken.T_FLOAT;
import static df.base.common.libs.ast.token.DefaultToken.T_INT;

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
        Object result = value;

        if (T_INT.equals(entry.token())) {
            result = Integer.parseInt((String) result);
        } else if (T_FLOAT.equals(entry.token())) {
            result = Double.parseDouble((String) result);
        }

        return result;
    }

}
