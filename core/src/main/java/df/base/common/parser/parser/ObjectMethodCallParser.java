package df.base.common.parser.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.parser.ExtendedToken;
import df.base.common.parser.ast.ObjectMethodNode;

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
