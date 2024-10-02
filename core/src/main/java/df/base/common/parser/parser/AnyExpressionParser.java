package df.base.common.parser.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;

import static df.base.common.libs.ast.token.DefaultToken.T_OPEN_CURLY_BRACE;
import static df.base.common.parser.DefaultMatcher.*;
import static df.base.common.parser.ExtendedToken.*;

public class AnyExpressionParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        Node valueNode;

        // resolve next expression after equal
        if (lexer.isNext(T_OPEN_CURLY_BRACE)) {
            // resolve array expression '{"string", "literal", etc.}'
            valueNode = context.getParser(ArrayParser.class).parse(lexer, context);
        } else if (lexer.isNext(T_ANNOTATION)) {
            // resolve nested annotation 'nextAnnotation=@AnnotationName(...)'
            shift(lexer, T_ANNOTATION);
            valueNode = context.getParser(AnnotationParser.class).parse(lexer, context);
        } else if (PARAMETERS.test(lexer)) {
            // parameters (key1=expression, ...)
            valueNode = context.getParser(ParametersParser.class).parse(lexer, context);
        } else if (lexer.isNext(T_CLASS_NAME)) {
            // resolve java-class name 'className=com.example.validator.NotNullValidator'
            shift(lexer, T_CLASS_NAME);
            valueNode = context.getParser(ClassNameParser.class).parse(lexer, context);
        } else if (lexer.isNext(T_VARIABLE)) {
            // either #variable, #staticMethod or #instance.method()

            // by default #localVariable
            Class<? extends Parser> variableParser = VariableParser.class;

            // if #object.methodCall() or // if #staticMethodCall()
            if (OBJECT_METHOD.test(lexer)) {
                variableParser = ObjectMethodCallParser.class;
            } else if (STATIC_METHOD.test(lexer)) {
                variableParser = FunctionCallParser.class;
            }

            valueNode = context.getParser(variableParser).parse(lexer, context);
        } else {
            // after all only literals available to parse 'value=100' || 'value="string"'
            valueNode = context.getParser(LiteralParser.class).parse(lexer, context);
        }

        parent.add(valueNode);
    }

}
