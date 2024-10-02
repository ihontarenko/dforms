package df.base.common.parser.parser;

import df.base.common.parser.ast.ParameterNode;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ast.ParametersNode;

import static df.base.common.libs.ast.token.DefaultToken.*;
import static df.base.common.parser.ExtendedToken.*;

public class ParametersParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ParametersNode parameters = new ParametersNode();

        shift(lexer, T_OPEN_BRACE);

        do {
            ensureNext(lexer, T_IDENTIFIER);

            ParameterNode parameter = new ParameterNode();
            // parser identifier and shift to equal symbol 'identifier='
            Node identifier = context.getParser(IdentifierParser.class).parse(lexer, context);
            parameter.setKey(identifier);
            shift(lexer, T_EQ);
            parameter.setValue(context.getParser(AnyExpressionParser.class).parse(lexer, context));

            // soft move
            lexer.moveNext(T_COMMA);

            parameters.addParameter(parameter);

        } while (lexer.isCurrent(T_COMMA));

        shift(lexer, T_CLOSE_BRACE);

        parent.add(parameters);
    }

}
