package df.base.common.libs.ast.rdp;

import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.rdp.tokenizer.ConditionTokenizer;
import df.base.common.libs.ast.rdp.tokenizer.IdentifierTokenizer;
import df.base.common.libs.ast.rdp.tokenizer.MethodCallTokenizer;
import df.base.common.libs.ast.rdp.tokenizer.SymbolTokenizer;
import df.base.common.libs.ast.token.Token;

import java.util.ArrayList;
import java.util.List;

public class TokenizerStrategyManager {

    private final List<TokenizerStrategy> strategies = new ArrayList<>();

    public TokenizerStrategyManager() {
        addStrategy(new IdentifierTokenizer());
        addStrategy(new ConditionTokenizer());
        addStrategy(new MethodCallTokenizer());
        addStrategy(new SymbolTokenizer());
    }

    public void addStrategy(TokenizerStrategy strategy) {
        strategies.add(strategy);
    }

    public Token.Entry getNextToken(String input, int position, TokenizerContext ctx) {
        for (TokenizerStrategy strategy : strategies) {
            if (strategy.matches(input, position, ctx)) {
                return strategy.tokenize(input, position, ctx);
            }
        }
        throw new RuntimeException("Не вдалося знайти відповідний токен.");
    }
}
