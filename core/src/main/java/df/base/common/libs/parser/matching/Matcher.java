package df.base.common.libs.parser.matching;

import df.base.common.libs.parser.token.DefaultToken;
import df.base.common.libs.parser.lexer.Lexer;

import java.util.function.Predicate;

public interface Matcher extends Predicate<Lexer> {

    Matcher MATH_TESTER = lexer -> lexer.isNext(DefaultToken.T_PLUS, DefaultToken.T_MINUS, DefaultToken.T_DIVIDE, DefaultToken.T_MULTIPLY);
    Matcher FIELD_PATH  = lexer -> {
        boolean caseA = lexer.sequence(DefaultToken.T_IDENTIFIER, DefaultToken.T_DOT, DefaultToken.T_IDENTIFIER);
        boolean caseB = lexer.sequence(DefaultToken.T_IDENTIFIER, DefaultToken.T_DOT, DefaultToken.T_MULTIPLY);

        return caseA || caseB;
    };

}
