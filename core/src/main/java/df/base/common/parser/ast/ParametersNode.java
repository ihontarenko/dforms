package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParametersNode extends EntryNode {

    private final List<ParameterNode> parameters = new ArrayList<>();

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public void addParameter(ParameterNode parameter) {
        this.parameters.add(parameter);
    }

    @Override
    public Object evaluate(EvaluationContext ctx) {
        Map<String, Object> parameters = new HashMap<>();

        for (ParameterNode parameter : getParameters()) {
            parameters.put((String) parameter.getKey().evaluate(ctx), parameter.getValue().evaluate(ctx));
        }

        return parameters;
    }
}
