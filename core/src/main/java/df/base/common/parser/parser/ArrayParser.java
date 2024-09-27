package df.base.common.parser.parser;

import df.base.common.parser.ast.AnnotationArrayNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;

import static df.base.common.parser.ExtendedToken.T_ANNOTATION;
import static df.base.common.libs.ast.token.DefaultToken.T_CLOSE_CURLY_BRACE;
import static df.base.common.libs.ast.token.DefaultToken.T_OPEN_CURLY_BRACE;

public class ArrayParser implements Parser {
    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, T_OPEN_CURLY_BRACE);

        AnnotationArrayNode arrayNode = new AnnotationArrayNode(lexer.lookAhead());

        lexer.moveNext(T_ANNOTATION);
        context.getParser(AnnotationParser.class).parse(lexer, arrayNode, context);

        parent.add(arrayNode);

        shift(lexer, T_CLOSE_CURLY_BRACE);
    }

}
