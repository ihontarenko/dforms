package df.base.common.libs.ast.rdp;

import df.base.common.libs.ast.rdp.context.InterpreterContext;
import df.base.common.libs.ast.rdp.context.ParserContext;
import df.base.common.libs.ast.rdp.context.TokenizerContext;
import df.base.common.libs.ast.rdp.node.EvaluableNode;
import df.base.common.libs.ast.token.Token;

import java.util.List;

public class Example {

    public static void main(String[] args) {
        // Ініціалізація стратегії та контекстів
        TokenizerStrategyManager strategyManager  = new TokenizerStrategyManager();
        TokenizerContext         tokenizerContext = new TokenizerContext();
        Tokenizer                tokenizer        = new Tokenizer(strategyManager);

        tokenizerContext.setTokenizerStrategyManager(strategyManager);

        String input = "#user[isNonNull].call();";

        // Токенізація всього виразу
        List<Token.Entry> tokens = tokenizer.tokenize(input, tokenizerContext);

        // Парсинг токенів
        ParserContext parserContext = new ParserContext();
        Parser        parser = new Parser(tokens);
        EvaluableNode root   = parser.parse(parserContext);

        // Інтерпретація AST
        InterpreterContext interpreterContext = new InterpreterContext();
        interpreterContext.setVariable("#user", "current_user");
        root.evaluate(interpreterContext);
    }

}
