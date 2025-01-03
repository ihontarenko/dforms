package df.common.parser.evaluation;

import df.common.invocable.Invocable;
import df.common.invocable.InvokeResult;
import df.common.invocable.StaticMethodInvocable;
import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.parser.EvaluationException;
import df.common.parser.ast.FunctionNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FunctionCompiler implements Compiler<FunctionNode, Object> {

    @Override
    public Object compile(FunctionNode node, EvaluationContext evaluationContext) {
        List<?>   arguments = new ArrayList<>();
        Method    method    = evaluationContext.getFunction(node.getMethodName());
        Invocable invocable = new StaticMethodInvocable(method);

        if (node.getArguments().evaluate(evaluationContext) instanceof List<?> rawList) {
            arguments = rawList;
        }

        invocable.addParameters(arguments);

        InvokeResult result = invocable.invoke();

        if (result.hasErrors()) {
            throw new EvaluationException("UNABLE TO INVOKE STATIC METHOD: %s".formatted(node.getMethodName()));
        }

        return result.getReturnValue();
    }

    @Override
    public Class<? extends FunctionNode> nodeType() {
        return FunctionNode.class;
    }

}
