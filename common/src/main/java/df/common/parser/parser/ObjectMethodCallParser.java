package df.common.parser.parser;

import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.ast.token.DefaultToken;
import df.common.parser.ExtendedToken;
import df.common.parser.ast.ObjectMethodNode;

public class ObjectMethodCallParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, ExtendedToken.T_VARIABLE);

        ObjectMethodNode node       = new ObjectMethodNode();
        String           objectName = lexer.current().value().substring(1);

        shift(lexer, DefaultToken.T_DOT);
        shift(lexer, DefaultToken.T_IDENTIFIER);

        String methodName = lexer.current().value();

        node.setObjectName(objectName);
        node.setMethodName(methodName);

        shift(lexer, DefaultToken.T_OPEN_BRACE);

        if (!lexer.isNext(DefaultToken.T_CLOSE_BRACE)) {
            node.setArguments(context.getParser(CommaSeparatedParser.class).parse(lexer, context));
        }

        shift(lexer, DefaultToken.T_CLOSE_BRACE);

        parent.add(node);
    }

}
