package df.base.common.libs.ast.rdp.tokenizer;

import df.base.common.libs.ast.rdp.TokenType;
import df.base.common.libs.ast.rdp.TokenizerStrategy;
import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.token.Token;

public class MethodCallTokenizer implements TokenizerStrategy {

    @Override
    public boolean matches(String input, int position, TokenizerContext ctx) {
        return input.startsWith(".call", position);
    }

    @Override
    public Token.Entry tokenize(String input, int position, TokenizerContext ctx) {
        return Token.Entry.of(TokenType.METHOD_CALL, ".call");
    }
}