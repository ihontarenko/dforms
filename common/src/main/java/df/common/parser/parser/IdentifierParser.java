package df.common.parser.parser;

import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.parser.ast.IdentifierNode;
import df.common.ast.token.DefaultToken;
import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);
        IdentifierNode node = new IdentifierNode(lexer.current());
        node.setIdentifier(node.entry().value());
        parent.add(node);
    }

}
