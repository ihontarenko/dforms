package df.base.common.libs.ast.rdp.node;

import df.base.common.libs.ast.rdp.context.InterpreterContext;

import java.util.ArrayList;
import java.util.List;

public class MethodCallNode extends AbstractEvaluableNode {

    private final EvaluableNode       object;
    private final String                      methodName;
    private final List<EvaluableNode> arguments;

    public MethodCallNode(EvaluableNode object, String methodName, List<EvaluableNode> arguments) {
        this.object = object;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public Object evaluate(InterpreterContext ctx) {
        Object       objectInstance = object.evaluate(ctx);
        List<Object> evaluatedArgs  = new ArrayList<>();

        for (EvaluableNode argument : arguments) {
            evaluatedArgs.add(argument.evaluate(ctx));
        }


        return "Результат методу";
    }
}
