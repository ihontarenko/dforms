package df.base.common.parser.parser;

import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ast.IdentifierNode;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);
        IdentifierNode node = new IdentifierNode(lexer.current());
        node.setIdentifier(node.entry().value());
        parent.add(node);
    }

}