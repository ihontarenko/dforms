package df.base.common.parameter.parsing;

import df.base.common.libs.ast.token.DefaultTokenizer;
import df.base.common.libs.ast.token.Token;

public class ParameterTokenizer extends DefaultTokenizer {

    @Override
    public Token.Entry entry(Token token, String value, int position, int ordinal) {
        return super.entry(token, value, position, ordinal);
    }

}
