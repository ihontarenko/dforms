package df.common.parser;

import df.common.ast.compiler.EvaluationContext;
import df.common.ast.compiler.EvaluationContextFactory;
import df.common.ast.lexer.Lexer;
import df.common.ast.node.Node;
import df.common.ast.node.RootNode;
import df.common.ast.parser.Parser;
import df.common.ast.parser.ParserContext;
import df.common.ast.token.Token;
import df.common.ast.token.Tokenizer;
import df.common.parser.evaluation.EvaluationContextConfigurator;
import df.common.parser.parser.AnyExpressionParser;
import df.common.parser.parser.ParserConfigurator;
import org.springframework.stereotype.Service;

import java.util.List;

public class ParserService {

    private final ParserContext     parserContext     = new ParserContext.SimpleContext();
    private final EvaluationContext evaluationContext = EvaluationContextFactory.defaultEvaluationContext();
    private final Tokenizer         tokenizer         = new DefaultTokenizer();
    private       Parser            parser;

    public ParserService() {
        initialize();
    }

    private void initialize() {
        new EvaluationContextConfigurator().configure(evaluationContext);
        new ParserConfigurator().configure(parserContext);
        new TokenizerConfigurator().configure(tokenizer);

        this.parser = parserContext.getParser(AnyExpressionParser.class);
    }

    public <N extends Node> N parse(String inputString) {
        Node              root    = new RootNode();
        List<Token.Entry> entries = tokenizer.tokenize(inputString);
        Lexer             lexer   = new DefaultLexer(entries);

        this.parser.parse(lexer, root, parserContext);

        return (N) root.first();
    }

    public <T> T compile(Node node, EvaluationContext context) {
        return (T) node.evaluate(context);
    }

    public EvaluationContext getEvaluationContext() {
        return evaluationContext;
    }
}
