package df.base.common.libs.parser.parser;

import df.base.common.libs.parser.token.DefaultToken;
import df.base.common.libs.parser.lexer.Lexer;
import df.base.common.libs.parser.node.EntryNode;
import df.base.common.libs.parser.node.Node;

public class IdentifierParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, DefaultToken.T_IDENTIFIER);
        parent.add(new EntryNode(lexer.current()));
    }

}
