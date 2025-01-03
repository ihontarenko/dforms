package df.common.parser;

import df.common.ast.token.Token;

public class DefaultTokenizer extends df.common.ast.token.DefaultTokenizer {

    @Override
    public Token.Entry entry(Token token, String value, int position, int ordinal) {
        return super.entry(token, value, position, ordinal);
    }

}
