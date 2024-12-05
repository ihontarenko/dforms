package df.base.common.parser.evaluation;

import df.base.common.invocable.Invocable;
import df.base.common.invocable.InvokeResult;
import df.base.common.invocable.StaticMethod;
import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.parser.EvaluationException;
import df.base.common.parser.ast.FunctionNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FunctionCompiler implements Compiler<FunctionNode> {

    @Override
    public Object compile(FunctionNode node, EvaluationContext ctx) {
        List<?>   arguments = new ArrayList<>();
        Method    method    = ctx.getFunction(node.getMethodName());
        Invocable invocable = new StaticMethod(method);

        if (node.getArguments().evaluate(ctx) instanceof List<?> rawList) {
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
