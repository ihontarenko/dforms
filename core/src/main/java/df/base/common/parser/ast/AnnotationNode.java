package df.base.common.parser.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.token.Token;

import java.util.HashMap;
import java.util.Map;

public class AnnotationNode extends EntryNode {
    public AnnotationNode(Token.Entry entry) {
        super(entry);
    }
}
