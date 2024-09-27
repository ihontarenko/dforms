package df.base.common.libs.ast.node.ast;

import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.EvaluationContext;
import df.base.common.libs.ast.token.Token;

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

    @Override
    public Object evaluate(EvaluationContext ctx) {
        return identifier;
    }
}
