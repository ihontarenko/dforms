package df.base.common.libs.ast.parser;

import df.base.common.libs.ast.node.ast.LiteralNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.token.Token;

import static df.base.common.libs.ast.token.DefaultToken.*;

public class LiteralParser implements Parser {

    public static final Token[] TOKENS = {T_INT, T_FLOAT, T_STRING, T_TRUE, T_FALSE, T_NULL};

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, TOKENS);

        Token.Entry entry   = lexer.current();
        LiteralNode literal = new LiteralNode(entry);

        literal.setValue(entry.value());

        if (entry.token() instanceof Enum<?> tokenName) {
            literal.property("type", tokenName.name());
        }

        parent.add(literal);
    }

}
