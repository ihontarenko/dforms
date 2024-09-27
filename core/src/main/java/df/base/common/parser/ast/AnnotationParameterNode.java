package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.ast.Identifier;
import df.base.common.libs.ast.token.Token;

public class AnnotationParameterNode extends EntryNode {

    private String key;
    private Node value;

    public AnnotationParameterNode(Token.Entry entry) {
        super(entry);
    }

    @Override
    public void add(Node node) {
        super.add(node);

        if (node instanceof Identifier identifier) {
            this.key = identifier.entry().value();
        } else {
            this.value = node;
        }
    }

    public String getKey() {
        return key;
    }

    public Node getValue() {
        return value;
    }

}
