package df.parent.library.parser.parser;

import df.parent.library.parser.lexer.Lexer;
import df.parent.library.parser.matching.Matcher;
import df.parent.library.parser.node.Node;
import df.parent.library.parser.token.Token;

public interface Parser {

    void parse(Lexer lexer, Node parent, ParserContext context);

    default boolean is(Lexer lexer, Matcher tester) {
        return tester.test(lexer.lexer());
    }

    default boolean isMath(Lexer lexer) {
        return is(lexer, Matcher.MATH_TESTER);
    }

    default boolean isFieldPath(Lexer lexer) {
        return is(lexer, Matcher.FIELD_PATH);
    }

    default void shift(Lexer lexer, Token... tokens) {
        if (lexer.isNext(tokens)) {
            lexer.next();
        } else {
            throwSyntaxErrorException(lexer, tokens);
        }
    }

    default void throwSyntaxErrorException(Lexer lexer, Token... token) {
        throw new SyntaxErrorException(this, lexer.lookAhead(), token);
    }

}
