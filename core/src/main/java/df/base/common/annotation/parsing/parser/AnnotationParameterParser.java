package df.base.common.annotation.parsing.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.IdentifierParser;
import df.base.common.libs.ast.parser.LiteralParser;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;

import static df.base.common.libs.ast.token.DefaultToken.*;

public class AnnotationParameterParser implements Parser {
    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        do {
            context.getParser(IdentifierParser.class).parse(lexer, parent, context);
            shift(lexer, T_EQ);
            context.getParser(LiteralParser.class).parse(lexer, parent, context);

        } while (lexer.isNext(T_COMMA));
    }

}
