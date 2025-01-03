package df.common.parser.evaluation;

import df.common.ast.compiler.Compiler;
import df.common.ast.compiler.EvaluationContext;
import df.common.parser.ast.ParameterNode;
import df.common.parser.ast.ParametersNode;

import java.util.HashMap;
import java.util.Map;

public class ParametersCompiler implements Compiler<ParametersNode, Map<String, Object>> {

    @Override
    public Map<String, Object> compile(ParametersNode node, EvaluationContext evaluationContext) {
        Map<String, Object> parameters = new HashMap<>();

        for (ParameterNode parameter : node.getParameters()) {
            parameters.put((String) parameter.getKey().evaluate(evaluationContext), parameter.getValue().evaluate(
                    evaluationContext));
        }

        return parameters;
    }

    @Override
    public Class<? extends ParametersNode> nodeType() {
        return ParametersNode.class;
    }

}