package df.base.internal.parser.parser;

import df.base.internal.parser.lexer.Lexer;
import df.base.internal.parser.node.EntryNode;
import df.base.internal.parser.node.Node;
import df.base.internal.parser.token.DefaultToken;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);
        parent.add(new EntryNode(lexer.current()));
    }

}
