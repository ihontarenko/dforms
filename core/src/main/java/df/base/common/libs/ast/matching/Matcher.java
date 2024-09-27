package df.base.common.libs.ast.matching;

import df.base.common.libs.ast.parser.LiteralParser;
import df.base.common.libs.ast.token.DefaultToken;
import df.base.common.libs.ast.lexer.Lexer;

import java.util.function.Predicate;

public interface Matcher extends Predicate<Lexer> {

    Matcher MATH_TESTER = lexer -> lexer.isNext(DefaultToken.T_PLUS, DefaultToken.T_MINUS, DefaultToken.T_DIVIDE, DefaultToken.T_MULTIPLY);
    Matcher LITERAL = lexer -> lexer.isNext(LiteralParser.TOKENS);

}
