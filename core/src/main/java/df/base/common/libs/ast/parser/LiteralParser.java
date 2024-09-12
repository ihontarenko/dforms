package df.base.common.libs.ast.parser;

import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.token.Token;

public class LiteralParser implements Parser {

    private static final Token[] TOKENS = {DefaultToken.T_INT, DefaultToken.T_FLOAT, DefaultToken.T_STRING, DefaultToken.T_TRUE, DefaultToken.T_FALSE, DefaultToken.T_NULL};

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, TOKENS);
        parent.add(new EntryNode(lexer.current()));
    }

}
