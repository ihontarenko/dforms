package df.common.parser.evaluation;

import df.common.invocable.Invocable;
import df.common.invocable.InvokeResult;
import df.common.invocable.ObjectMethodInvocable;
import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.ast.node.Node;
import df.common.parser.EvaluationException;
import df.common.parser.ast.ObjectMethodNode;
import df.common.reflection.Reflections;

import java.util.ArrayList;
import java.util.List;

public class MethodCompiler implements Compiler<ObjectMethodNode, Object> {

    @Override
    public Object compile(ObjectMethodNode node, EvaluationContext evaluationContext) {
        List<?> parameterValues = new ArrayList<>();
        Node    parametersNode  = node.getArguments();

        if (parametersNode != null && parametersNode.evaluate(evaluationContext) instanceof List<?> rawList) {
            parameterValues = rawList;
        }

        Class<?>[] parametersTypes = Reflections.getArgumentsTypes(parameterValues.toArray(Object[]::new));
        Object     object          = evaluationContext.requireVariable(node.getObjectName());
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
