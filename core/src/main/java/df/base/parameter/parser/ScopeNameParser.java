package df.base.parameter.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.EntryNode;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.IdentifierParser;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.parameter.ast.ParameterNode;

import static df.base.common.libs.ast.token.DefaultToken.T_CLOSE_BRACE;
import static df.base.common.libs.ast.token.DefaultToken.T_OPEN_BRACE;

public class ScopeNameParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, T_OPEN_BRACE);
        context.getParser(IdentifierParser.class).parse(lexer, parent, context);
        shift(lexer, T_CLOSE_BRACE);
    }

}
