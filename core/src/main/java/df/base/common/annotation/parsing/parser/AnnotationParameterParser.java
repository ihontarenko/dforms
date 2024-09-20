package df.base.common.annotation.parsing.parser;

import df.base.common.annotation.parsing.ast.AnnotationParameterNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.IdentifierParser;
import df.base.common.libs.ast.parser.LiteralParser;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;

import static df.base.common.annotation.parsing.AnnotationToken.T_ANNOTATION;
import static df.base.common.annotation.parsing.AnnotationToken.T_ANNOTATION_CLASS_NAME;
import static df.base.common.libs.ast.token.DefaultToken.*;

public class AnnotationParameterParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        do {
            ensureNext(lexer, T_IDENTIFIER);

            AnnotationParameterNode parameter = new AnnotationParameterNode(lexer.lookAhead());

            // parser identifier and shift to equal symbol 'identifier='
            context.getParser(IdentifierParser.class).parse(lexer, parameter, context);
            shift(lexer, T_EQ);

            // resolve next expression after equal
            if (lexer.isNext(T_OPEN_CURLY_BRACE)) {
                // resolve array expression '{"string", "literal", etc.}'
                context.getParser(AnnotationArrayParser.class).parse(lexer, parameter, context);
            } else if (lexer.isNext(T_ANNOTATION)) {
                // resolve nested annotation 'nextAnnotation=@AnnotationName(...)'
                shift(lexer, T_ANNOTATION);
                context.getParser(AnnotationParser.class).parse(lexer, parameter, context);
            } else if (lexer.isNext(T_ANNOTATION_CLASS_NAME)) {
                // resolve java-class name 'className=com.example.validator.NotNullValidator'
                shift(lexer, T_ANNOTATION_CLASS_NAME);
                context.getParser(AnnotationClassNameParser.class).parse(lexer, parameter, context);
            } else {
                // after all only literals available to parse 'value=100' || 'value="string"'
                context.getParser(LiteralParser.class).parse(lexer, parameter, context);
            }

            if (lexer.isNext(T_COMMA)) {
                lexer.next();
            }

            parent.add(parameter);

        } while (lexer.isCurrent(T_COMMA));
    }

}
