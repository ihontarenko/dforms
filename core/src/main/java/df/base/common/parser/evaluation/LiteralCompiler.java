package df.base.common.parser.evaluation;

import df.base.common.ast.compiler.Compiler;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.LiteralNode;

import static df.base.common.ast.token.DefaultToken.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class LiteralCompiler implements Compiler<LiteralNode> {

    @Override
    public Object compile(LiteralNode node, EvaluationContext ctx) {
        Object  result = node.getValue();
        Enum<?> token  = (Enum<?>) node.entry().token();

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

    @Override
    public Class<? extends LiteralNode> nodeType() {
        return LiteralNode.class;
    }

}
