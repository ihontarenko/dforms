package df.base.common.libs.ast.node.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.token.Token;

public class Identifier extends EntryNode {

    private final String name;
    public Identifier(Token.Entry entry) {
        super(entry);
        this.name = entry.value();
    }

    public String getName() {
        return name;
    }

}