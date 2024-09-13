package df.base.parameter.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.token.Token;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParameterNode extends EntryNode {

    public ParameterNode(Token.Entry entry) {
        super(entry);
    }

    public String getParameterName() {
        return entry.value().substring(1);
    }

    public List<String> getParameterValues() {
        return Arrays.stream(children()).map(node -> ((EntryNode)node).entry().value()).toList();
    }

}
