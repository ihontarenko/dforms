package df.base.common.parser.ast;

import df.base.common.ast.node.EntryNode;
import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.ast.token.Token;

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
