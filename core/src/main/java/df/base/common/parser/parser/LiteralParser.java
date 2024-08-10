package df.base.common.parser.parser;

import df.base.common.parser.lexer.Lexer;
import df.base.common.parser.node.EntryNode;
import df.base.common.parser.node.Node;
import df.base.common.parser.token.Token;

import static df.base.common.parser.token.DefaultToken.*;

public class LiteralParser implements Parser {

    private static final Token[] TOKENS = {T_INT, T_FLOAT, T_STRING, T_TRUE, T_FALSE, T_NULL};

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, TOKENS);
        parent.add(new EntryNode(lexer.current()));
    }

}
