package df.base.common.parser.parser;

import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.node.Node;
import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;
import df.base.common.ast.token.DefaultToken;
import df.base.common.parser.ExtendedToken;
import df.base.common.parser.ast.FunctionNode;

public class FunctionCallParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        shift(lexer, ExtendedToken.T_VARIABLE);

        FunctionNode node       = new FunctionNode();
        String       methodName = lexer.current().value().substring(1);

        node.setMethodName(methodName);

        shift(lexer, DefaultToken.T_OPEN_BRACE);

        if (!lexer.isNext(DefaultToken.T_CLOSE_BRACE)) {
            node.setArguments(context.getParser(CommaSeparatedParser.class).parse(lexer, context));
        }

        shift(lexer, DefaultToken.T_CLOSE_BRACE);

        parent.add(node);
    }

}
