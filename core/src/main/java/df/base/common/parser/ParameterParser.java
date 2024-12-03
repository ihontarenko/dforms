package df.base.common.parser;

import df.base.common.parser.configurator.ParserConfigurator;
import df.base.common.parser.configurator.TokenizerConfigurator;
import df.base.common.parser.parser.AnyExpressionParser;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.node.Node;
import df.base.common.libs.ast.node.RootNode;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Token;
import df.base.common.libs.ast.token.Tokenizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterParser {

    private final ParserContext context   = new ParserContext.SimpleContext();
    private final Tokenizer     tokenizer = new DefaultTokenizer();
    private       Parser        parser;

    public ParameterParser() {
        initialize();
    }

    private void initialize() {
        new ParserConfigurator().configure(context);
        new TokenizerConfigurator().configure(tokenizer);

        this.parser = context.getParser(AnyExpressionParser.class);
    }

    public Node parse(String inputString) {
        Node              root    = new RootNode();
        List<Token.Entry> entries = tokenizer.tokenize(inputString);
        Lexer             lexer   = new DefaultLexer(entries);

        this.parser.parse(lexer, root, context);

        return root;
    }

}
