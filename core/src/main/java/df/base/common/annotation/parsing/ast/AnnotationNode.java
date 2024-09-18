package df.base.common.annotation.parsing.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.token.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationNode extends EntryNode {

    private final Map<String, Node> parameters = new HashMap<>();

    public AnnotationNode(Token.Entry entry) {
        super(entry);
    }

    @Override
    public void add(Node node) {
        super.add(node);

        if (node instanceof AnnotationParameterNode parameterNode) {
            parameters.put(parameterNode.getKey(), parameterNode.getValue());
        }
    }
}
