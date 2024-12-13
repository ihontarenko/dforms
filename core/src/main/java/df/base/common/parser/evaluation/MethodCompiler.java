package df.base.common.parser.evaluation;

import df.base.common.invocable.Invocable;
import df.base.common.invocable.InvokeResult;
import df.base.common.invocable.ObjectMethodInvocable;
import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.libs.ast.node.Node;
import df.base.common.parser.EvaluationException;
import df.base.common.parser.ast.ObjectMethodNode;
import df.base.common.reflection.Reflections;

import java.util.ArrayList;
import java.util.List;

public class MethodCompiler implements Compiler<ObjectMethodNode> {

    @Override
    public Object compile(ObjectMethodNode node, EvaluationContext ctx) {
        List<?> parameterValues = new ArrayList<>();
        Node    parametersNode  = node.getArguments();

        if (parametersNode != null && parametersNode.evaluate(ctx) instanceof List<?> rawList) {
            parameterValues = rawList;
        }

        Class<?>[] parametersTypes = Reflections.getArgumentsTypes(parameterValues.toArray(Object[]::new));
        Object     object          = ctx.requireVariable(node.getObjectName());
        Invocable  invocable       = new ObjectMethodInvocable(object, node.getMethodName(), parametersTypes);

        invocable.addParameters(parameterValues);

        InvokeResult result = invocable.invoke();

        if (result.hasErrors()) {
            throw new EvaluationException("UNABLE TO INVOKE OBJECT METHOD: %s".formatted(node.getMethodName()));
        }

        return result.getReturnValue();
    }

    @Override
    public Class<? extends ObjectMethodNode> nodeType() {
        return ObjectMethodNode.class;
    }

}
