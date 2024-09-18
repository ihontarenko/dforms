package df.base.common.libs.ast.parser;

import df.base.common.libs.ast.node.ast.Identifier;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.Node;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);

        if (parent instanceof EntryNode entryNode) {
            entryNode.property(DefaultToken.T_IDENTIFIER.name(), lexer.current().value());
        }

        parent.add(new Identifier(lexer.current()));
    }

}
