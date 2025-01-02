package df.base.common.parser.parser;

import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.node.Node;
import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;

import static df.base.common.ast.token.DefaultToken.*;

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
