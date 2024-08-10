package df.base.common.parser.parser;

import df.base.common.parser.lexer.Lexer;
import df.base.common.parser.node.EntryNode;
import df.base.common.parser.node.Node;
import df.base.common.parser.token.DefaultToken;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);
        parent.add(new EntryNode(lexer.current()));
    }

}
