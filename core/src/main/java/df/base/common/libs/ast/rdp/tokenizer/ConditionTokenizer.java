package df.base.common.libs.ast.rdp.tokenizer;

import df.base.common.libs.ast.rdp.TokenType;
import df.base.common.libs.ast.rdp.TokenizerStrategy;
import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.token.Token;

public class ConditionTokenizer implements TokenizerStrategy {

    @Override
    public boolean matches(String input, int position, TokenizerContext ctx) {
        return input.charAt(position) == '[';
    }

    @Override
    public Token.Entry tokenize(String input, int position, TokenizerContext ctx) {
        StringBuilder builder = new StringBuilder();

        builder.append(input.charAt(position));
        position++;

        while (position < input.length() && input.charAt(position) != ']') {
            builder.append(input.charAt(position));
            position++;
        }

        builder.append(input.charAt(position));
        position++;

        return Token.Entry.of(TokenType.CONDITION, builder.toString());
    }
}
