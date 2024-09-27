package df.base.common.parser.parser;

import df.base.common.parser.DefaultMatcher;
import df.base.common.parser.ast.ParameterNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.IdentifierParser;
import df.base.common.libs.ast.parser.LiteralParser;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ast.ParametersNode;

import static df.base.common.libs.ast.token.DefaultToken.*;
import static df.base.common.parser.ExtendedToken.*;

public class ParametersParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ParametersNode parameters = new ParametersNode();

        do {
            ensureNext(lexer, T_IDENTIFIER);

            ParameterNode parameter = new ParameterNode();

            // parser identifier and shift to equal symbol 'identifier='
            Node identifier = context.getParser(IdentifierParser.class).parse(lexer, context);
            parameter.setKey(identifier);

            shift(lexer, T_EQ);

            Node valueNode;

            // resolve next expression after equal
            if (lexer.isNext(T_OPEN_CURLY_BRACE)) {
                // resolve array expression '{"string", "literal", etc.}'
                valueNode = context.getParser(ArrayParser.class).parse(lexer, context);
            } else if (lexer.isNext(T_ANNOTATION)) {
                // resolve nested annotation 'nextAnnotation=@AnnotationName(...)'
                shift(lexer, T_ANNOTATION);
                valueNode = context.getParser(AnnotationParser.class).parse(lexer, context);
            } else if (lexer.isNext(T_CLASS_NAME)) {
                // resolve java-class name 'className=com.example.validator.NotNullValidator'
                shift(lexer, T_CLASS_NAME);
                valueNode = context.getParser(ClassNameParser.class).parse(lexer, context);
            } else if (lexer.isNext(T_VARIABLE)) {
                // either #variable, #staticMethod or #instance.method()
                valueNode = context.getParser(ExternalVariableParser.class).parse(lexer, context);
            } else {
                // after all only literals available to parse 'value=100' || 'value="string"'
                valueNode = context.getParser(LiteralParser.class).parse(lexer, context);
            }

            parameter.setValue(valueNode);

            // soft move
            lexer.moveNext(T_COMMA);

            parameters.addParameter(parameter);

        } while (lexer.isCurrent(T_COMMA));

        parent.add(parameters);
    }

}
