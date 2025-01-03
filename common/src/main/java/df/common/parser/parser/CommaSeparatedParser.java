package df.common.parser.parser;

import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.parser.ast.ValuesNode;

import static df.common.ast.token.DefaultToken.*;

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
