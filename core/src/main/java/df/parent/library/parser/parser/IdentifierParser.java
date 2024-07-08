package df.parent.library.parser.parser;

import df.parent.library.parser.lexer.Lexer;
import df.parent.library.parser.node.EntryNode;
import df.parent.library.parser.node.Node;
import df.parent.library.parser.token.DefaultToken;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);
        parent.add(new EntryNode(lexer.current()));
    }

}
