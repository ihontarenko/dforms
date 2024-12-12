package df.base.common.parser.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ast.StringDefinitionNode;

import static df.base.common.libs.ast.token.DefaultToken.T_DIVIDE;

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
