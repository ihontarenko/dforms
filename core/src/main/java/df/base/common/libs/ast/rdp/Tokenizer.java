package df.base.common.libs.ast.rdp;

import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.token.Token;

import java.util.ArrayList;
import java.util.List;

class Tokenizer {

    private TokenizerStrategyManager strategyManager;

    public Tokenizer(TokenizerStrategyManager strategyManager) {
        this.strategyManager = strategyManager;
    }

    public List<Token.Entry> tokenize(String input, TokenizerContext ctx) {
        List<Token.Entry> entries   = new ArrayList<>();
        int               position = 0;

        while (position < input.length()) {
            Token.Entry entry = strategyManager.getNextToken(input, position, ctx);
            entries.add(entry);
            position += entry.value().length();
        }

        entries.add(Token.Entry.of(TokenType.EOF, ""));

        return entries;
    }
}
