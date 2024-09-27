package df.base.common.parser;

import df.base.common.libs.ast.token.Token;

public class DefaultTokenizer extends df.base.common.libs.ast.token.DefaultTokenizer {

    @Override
    public Token.Entry entry(Token token, String value, int position, int ordinal) {
        return super.entry(token, value, position, ordinal);
    }

}
