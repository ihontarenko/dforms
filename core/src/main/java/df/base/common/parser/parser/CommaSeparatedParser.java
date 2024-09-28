package df.base.common.parser.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.matching.Matcher;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.parser.ast.ValuesNode;

import static df.base.common.libs.ast.token.DefaultToken.*;
import static df.base.common.parser.ExtendedToken.T_ANNOTATION;
import static df.base.common.parser.ExtendedToken.T_VARIABLE;

public class CommaSeparatedParser implements Parser {

    @Override
    public void parse(Lexer lexer, Node parent, ParserContext context) {
        ValuesNode valuesNode = new ValuesNode();

        do {
            Node valueNode = null;

            if (lexer.isNext(T_ANNOTATION)) {
                shift(lexer, T_ANNOTATION);
                valueNode = context.getParser(AnnotationParser.class).parse(lexer, context);
            } else if (is(lexer, Matcher.LITERAL)) {
                valueNode = context.getParser(LiteralParser.class).parse(lexer, context);
            } else if (lexer.isNext(T_VARIABLE)) {
                valueNode = context.getParser(ExternalVariableParser.class).parse(lexer, context);
            } else {
                throwSyntaxErrorException(lexer, 0, LiteralParser.TOKENS);
            }

            valuesNode.addElement(valueNode);

            // soft move
            lexer.moveNext(T_COMMA);

        } while (lexer.isCurrent(T_COMMA));

        parent.add(valuesNode);
    }

}
