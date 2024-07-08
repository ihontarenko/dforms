package df.parent.library.parser.matching;

import df.parent.library.parser.lexer.Lexer;

import java.util.function.Predicate;

import static df.parent.library.parser.token.DefaultToken.*;

public interface Matcher extends Predicate<Lexer> {

    Matcher MATH_TESTER = lexer -> lexer.isNext(T_PLUS, T_MINUS, T_DIVIDE, T_MULTIPLY);
    Matcher FIELD_PATH  = lexer -> {
        boolean caseA = lexer.sequence(T_IDENTIFIER, T_DOT, T_IDENTIFIER);
        boolean caseB = lexer.sequence(T_IDENTIFIER, T_DOT, T_MULTIPLY);

        return caseA || caseB;
    };

}
