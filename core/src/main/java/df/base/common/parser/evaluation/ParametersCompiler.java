package df.base.common.parser.evaluation;

import df.base.common.libs.ast.compiler.Compiler;
import df.base.common.libs.ast.compiler.EvaluationContext;
import df.base.common.parser.ast.ParameterNode;
import df.base.common.parser.ast.ParametersNode;

import java.util.HashMap;
import java.util.Map;

public class ParametersCompiler implements Compiler<ParametersNode> {

    @Override
    public Object compile(ParametersNode node, EvaluationContext ctx) {
        Map<String, Object> parameters = new HashMap<>();

        for (ParameterNode parameter : node.getParameters()) {
            parameters.put((String) parameter.getKey().evaluate(ctx), parameter.getValue().evaluate(ctx));
        }

        return parameters;
    }

    @Override
    public Class<? extends ParametersNode> nodeType() {
        return ParametersNode.class;
    }

}
