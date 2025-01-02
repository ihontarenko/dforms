package df.base.common.parser.ast;

import df.base.common.ast.node.EntryNode;
import df.base.common.ast.compiler.EvaluationContext;

import java.util.ArrayList;
import java.util.List;

public class ParametersNode extends EntryNode {

    private final List<ParameterNode> parameters = new ArrayList<>();

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public void addParameter(ParameterNode parameter) {
        this.parameters.add(parameter);
    }

}
