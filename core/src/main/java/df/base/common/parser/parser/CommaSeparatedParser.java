package df.base.common.parser.parser;

import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.node.Node;
import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;
import df.base.common.parser.ast.ValuesNode;

import static df.base.common.ast.token.DefaultToken.*;

public class CommaSeparatedParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ValuesNode valuesNode = new ValuesNode();

        do {
            valuesNode.addElement(
                    context.getParser(AnyExpressionParser.class).parse(lexer, context));

            lexer.moveNext(T_COMMA);

        } while (lexer.isCurrent(T_COMMA));

        parent.add(valuesNode);
    }

}
