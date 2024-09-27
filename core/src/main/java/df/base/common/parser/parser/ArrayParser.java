package df.base.common.parser.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.matching.Matcher;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.LiteralParser;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ast.ValuesNode;

import static df.base.common.libs.ast.token.DefaultToken.*;
import static df.base.common.parser.ExtendedToken.T_ANNOTATION;

public class ArrayParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, T_OPEN_CURLY_BRACE);

        if (!lexer.isNext(T_CLOSE_CURLY_BRACE)) {
            parent.add(context.getParser(CommaSeparatedParser.class).parse(lexer, context));
        }

        shift(lexer, T_CLOSE_CURLY_BRACE);
    }

}
