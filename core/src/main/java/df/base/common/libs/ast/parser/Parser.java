package df.base.common.libs.ast.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.matching.Matcher;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.token.Token;

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
