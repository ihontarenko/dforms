package df.base.common.parameter.parsing.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.token.Token;
import df.base.common.parameter.Parameter;
import df.base.common.parameter.ParameterValueNotFoundException;

import java.util.Arrays;
import java.util.List;

public class ParameterNode extends EntryNode implements Parameter<String> {

    public ParameterNode(Token.Entry entry) {
        super(entry);
    }

    public String getParameterName() {
        return entry.value().substring(1);
    }

    @Override
    public String getParameterValue() {
        return getParameterValues().stream().findFirst().orElseThrow(()
                -> new ParameterValueNotFoundException("NO PARAMETER[%s] VALUE".formatted(getParameterName())));
    }

    @Override
    public List<String> getParameterValues() {
        return Arrays.stream(children()).map(node -> ((EntryNode)node).entry().value()).toList();
    }

}
