package df.base.common.annotation.parsing.parser;

import df.base.common.annotation.parsing.ast.AnnotationNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;

import static df.base.common.annotation.parsing.AnnotationToken.T_ANNOTATION;
import static df.base.common.libs.ast.token.DefaultToken.*;

public class AnnotationParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ensureCurrent(lexer, T_ANNOTATION);

        AnnotationNode annotation = new AnnotationNode(lexer.current());

        shift(lexer, T_OPEN_BRACE);

        if (lexer.isNext(T_IDENTIFIER)) {
            context.getParser(AnnotationParameterParser.class).parse(lexer, annotation, context);
        }

        shift(lexer, T_CLOSE_BRACE);

        parent.add(annotation);
    }

}
