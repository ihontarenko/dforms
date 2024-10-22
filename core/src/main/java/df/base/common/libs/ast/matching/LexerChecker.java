package df.base.common.libs.ast.matching;

import df.base.common.parser.parser.LiteralParser;
import df.base.common.libs.ast.lexer.Lexer;

import java.util.function.Predicate;

import static df.base.common.libs.ast.token.DefaultToken.*;

public interface LexerChecker extends Predicate<Lexer> {

    LexerChecker MATH_TESTER = lexer
            -> lexer.isNext(T_PLUS, T_MINUS, T_DIVIDE, T_MULTIPLY);
    LexerChecker LITERAL     = lexer
            -> lexer.isNext(LiteralParser.TOKENS);

}
