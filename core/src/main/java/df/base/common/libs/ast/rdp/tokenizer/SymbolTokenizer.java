package df.base.common.libs.ast.rdp.tokenizer;

import df.base.common.libs.ast.rdp.TokenType;
import df.base.common.libs.ast.rdp.TokenizerStrategy;
import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.token.Token;

public class SymbolTokenizer implements TokenizerStrategy {

    @Override
    public boolean matches(String input, int position, TokenizerContext ctx) {
        char c = input.charAt(position);
        return c == ';' || c == '.' || c == '(' || c == ')';
    }

    @Override
    public Token.Entry tokenize(String input, int position, TokenizerContext ctx) {
        return Token.Entry.of(TokenType.SYMBOL, String.valueOf(input.charAt(position)));
    }

}
