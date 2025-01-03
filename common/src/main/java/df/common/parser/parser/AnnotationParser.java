package df.common.parser.parser;

import df.common.parser.ast.AnnotationNode;
import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;

import static df.common.parser.ExtendedToken.T_ANNOTATION;
import static df.common.ast.token.DefaultToken.*;

public class AnnotationParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ensureNext(lexer, T_ANNOTATION);

        AnnotationNode annotation = new AnnotationNode(lexer.current());

        shift(lexer, T_OPEN_BRACE);

        if (lexer.isNext(T_IDENTIFIER)) {
            context.getParser(ParametersParser.class).parse(lexer, annotation, context);
        }

        shift(lexer, T_CLOSE_BRACE);

        parent.add(annotation);
    }

}
