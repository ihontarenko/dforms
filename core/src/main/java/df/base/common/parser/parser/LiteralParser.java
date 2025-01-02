package df.base.common.parser.parser;

import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;
import df.base.common.parser.ast.LiteralNode;
import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.node.Node;
import df.base.common.ast.token.Token;

import static df.base.common.ast.token.DefaultToken.*;

public class LiteralParser implements Parser {

    public static final Token[] TOKENS = {T_INT, T_FLOAT, T_STRING, T_TRUE, T_FALSE, T_NULL};

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, TOKENS);

        Token.Entry entry   = lexer.current();
        LiteralNode literal = new LiteralNode(entry);

        literal.setValue(entry.value());

        if (entry.token() instanceof Enum<?> tokenName) {
            literal.setAttribute("type", tokenName.name());
        }

        parent.add(literal);
    }

}
