package df.base.common.libs.ast.node.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.jbm.ReflectionUtils;
import df.base.common.libs.jbm.bean.MethodNotFoundException;

import java.lang.reflect.Method;
import java.util.List;

public class ObjectMethodNode extends EntryNode {

    private String objectName;
    private String methodName;
    private Node   arguments;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

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
    public Object evaluate(EvaluationContext ctx) {
        Object[]   arguments     = ((List<?>) this.arguments.evaluate(ctx)).toArray(Object[]::new);
        Class<?>[] argumentTypes = ReflectionUtils.getArgumentsTypes(arguments);

        Object object = ctx.requireVariable(objectName);
        Method method = ReflectionUtils.getMethod(object.getClass(), methodName, argumentTypes)
                .orElseThrow(() -> new MethodNotFoundException("NO SUCH METHOD '%s' FOUND".formatted(methodName)));

        return ReflectionUtils.invokeMethod(object, method, arguments);
    }

}
