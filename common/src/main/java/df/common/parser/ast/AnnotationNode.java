package df.common.parser.ast;

import df.common.ast.node.EntryNode;
import df.common.ast.token.Token;

public class AnnotationNode extends EntryNode {
    public AnnotationNode(Token.Entry entry) {
        super(entry);
    }
}
