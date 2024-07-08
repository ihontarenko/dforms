package df.parent.library.parser.parser;

import df.parent.library.parser.lexer.Lexer;
import df.parent.library.parser.node.EntryNode;
import df.parent.library.parser.node.Node;
import df.parent.library.parser.token.Token;

import static df.parent.library.parser.token.DefaultToken.*;

public class LiteralParser implements Parser {

    private static final Token[] TOKENS = {T_INT, T_FLOAT, T_STRING, T_TRUE, T_FALSE, T_NULL};

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, TOKENS);
        parent.add(new EntryNode(lexer.current()));
    }

}
