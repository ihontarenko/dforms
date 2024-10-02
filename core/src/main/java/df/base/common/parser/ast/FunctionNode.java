package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.jbm.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

public class FunctionNode extends EntryNode {

    private String     methodName;
    private Node arguments;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Node getArguments() {
        return arguments;
    }

    public void setArguments(Node arguments) {
        this.arguments = arguments;
    }

    @Override
    public Object evaluate(EvaluationContext evaluationContext) {
        Object[] arguments = new Object[0];
        Method   method    = evaluationContext.getFunction(methodName);

        if (this.arguments != null) {
            arguments = ((List<?>) this.arguments.evaluate(evaluationContext)).toArray(Object[]::new);
        }

        return ReflectionUtils.invokeMethod(null, method, arguments);
    }

}
