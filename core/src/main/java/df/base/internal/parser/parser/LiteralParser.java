package df.base.internal.parser.parser;

import df.base.internal.parser.lexer.Lexer;
import df.base.internal.parser.node.EntryNode;
import df.base.internal.parser.node.Node;
import df.base.internal.parser.token.Token;

import static df.base.internal.parser.token.DefaultToken.*;

public class LiteralParser implements Parser {

    private static final Token[] TOKENS = {T_INT, T_FLOAT, T_STRING, T_TRUE, T_FALSE, T_NULL};

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, TOKENS);
        parent.add(new EntryNode(lexer.current()));
    }

}
