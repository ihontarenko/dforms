package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.token.Token;

import static df.base.common.libs.ast.token.DefaultToken.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
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
        Object  result = value;
        Enum<?> token  = (Enum<?>) entry.token();

        if (T_INT.equals(token)) {
            result = parseInt((String) result);
        } else if (T_FLOAT.equals(token)) {
            String value = (String) result;
            int decimalIndex = value.indexOf('.');
            if (decimalIndex != -1) {
                if ((value.length() - decimalIndex - 1) <= 7) {
                    result = parseFloat(value);
                } else {
                    result = parseDouble(value);
                }
            } else {
                result = parseDouble(value);
            }
        } else if (T_NULL.equals(token)) {
            result = null;
        } else if (T_FALSE.equals(token) || T_TRUE.equals(token)) {
            result = parseBoolean((String) result);
        }

        return result;
    }

}
