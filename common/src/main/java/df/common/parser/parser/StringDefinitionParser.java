package df.common.parser.parser;

import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.parser.ast.StringDefinitionNode;

import static df.common.ast.token.DefaultToken.T_DIVIDE;

public class StringDefinitionParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        StringDefinitionNode node = new StringDefinitionNode();

        node.setHandler(context.getParser(VariableParser.class).parse(lexer, context));
        shift(lexer, T_DIVIDE);
        node.setCommand(context.getParser(IdentifierParser.class).parse(lexer, context));

        parent.add(node);
    }

}
