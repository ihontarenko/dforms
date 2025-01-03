package df.common.parser.ast;

import df.common.ast.node.EntryNode;
import df.common.ast.token.Token;

public class IdentifierNode extends EntryNode  {

    private String identifier;

    public IdentifierNode(Token.Entry entry) {
        super(entry);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
