package df.base.common.parser;

import df.base.common.libs.ast.matching.Matcher;
import df.base.common.libs.ast.token.DefaultToken;

final public class DefaultMatcher {

    public static final Matcher VARIABLE = lexer
            -> lexer.isNext(ExtendedToken.T_VARIABLE);
    public static final Matcher STATIC_METHOD = lexer
            -> lexer.sequence(ExtendedToken.T_VARIABLE, DefaultToken.T_OPEN_BRACE);
    public static final Matcher OBJECT_METHOD = lexer
            -> lexer.sequence(ExtendedToken.T_VARIABLE, DefaultToken.T_DOT, DefaultToken.T_IDENTIFIER, DefaultToken.T_OPEN_BRACE);
    public static final Matcher PARAMETERS = lexer
            -> lexer.sequence(DefaultToken.T_OPEN_BRACE, DefaultToken.T_IDENTIFIER, DefaultToken.T_EQ);

}
