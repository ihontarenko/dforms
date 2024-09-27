package df.base.common.libs.ast.node.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.token.Token;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StaticMethod extends EntryNode {

    public StaticMethod(Token.Entry entry) {
        super(entry);
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        List<Object> arguments = new ArrayList<>();

        for (Node child : children()) {
            arguments.add(child.evaluate(ctx));
        }

        Object method = ctx.getVariable(entry.value());
        Object result = null;

        try {
            if (method instanceof Method methodInstance) {
                result = methodInstance.invoke(null, arguments.toArray(Object[]::new));
            }
        } catch (Exception exception) { }

        return result;
    }

}
