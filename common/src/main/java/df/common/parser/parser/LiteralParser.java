package df.common.parser.parser;

import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.parser.ast.LiteralNode;
import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.token.Token;

import static df.common.ast.token.DefaultToken.*;

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
