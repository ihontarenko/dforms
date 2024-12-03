package df.base.common.parser.ast;

import df.base.common.invocable.Invocable;
import df.base.common.invocable.InvokeResult;
import df.base.common.invocable.ObjectMethod;
import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.parser.EvaluationException;
import df.base.common.reflection.Reflections;

import java.util.Collection;
import java.util.Collections;

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
    public Object evaluate(EvaluationContext evaluationContext) {
        Collection<?> parameterValues = this.arguments == null ? Collections.emptyList()
                : (Collection<?>) this.arguments.evaluate(evaluationContext);
        Class<?>[]    parametersTypes = Reflections.getArgumentsTypes(parameterValues.toArray(Object[]::new));
        Object        object          = evaluationContext.requireVariable(objectName);

        Invocable invocable = new ObjectMethod(object, methodName, parametersTypes);

        invocable.addParameters(parameterValues);

        InvokeResult result = invocable.invoke();

        if (result.hasErrors()) {
            throw new EvaluationException(result.getError(null).message());
        }

        return result.getReturnValue();
    }

}
