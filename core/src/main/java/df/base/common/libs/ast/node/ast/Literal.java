package df.base.common.libs.ast.node.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.token.Token;

public class Literal extends EntryNode {

    private final Object value;

    public Literal(Token.Entry entry) {
        super(entry);
        this.value = entry.value();
    }

    public Object getValue() {
        return value;
    }
}
