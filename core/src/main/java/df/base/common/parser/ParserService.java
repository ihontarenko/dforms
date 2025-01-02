package df.base.common.parser;

import df.base.common.ast.compiler.EvaluationContext;
import df.base.common.ast.compiler.EvaluationContextFactory;
import df.base.common.ast.lexer.Lexer;
import df.base.common.ast.node.Node;
import df.base.common.ast.node.RootNode;
import df.base.common.ast.parser.Parser;
import df.base.common.ast.parser.ParserContext;
import df.base.common.ast.token.Token;
import df.base.common.ast.token.Tokenizer;
import df.base.common.parser.evaluation.EvaluationContextConfigurator;
import df.base.common.parser.parser.AnyExpressionParser;
import df.base.common.parser.parser.ParserConfigurator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
