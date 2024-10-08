package df.base.common.parser;

import df.base.common.parser.configurator.DefaultParserConfigurator;
import df.base.common.parser.configurator.DefaultTokenizerConfigurator;
import df.base.common.parser.parser.ParametersParser;
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
        new DefaultParserConfigurator().configure(context);
        new DefaultTokenizerConfigurator().configure(tokenizer);

        this.parser = context.getParser(ParametersParser.class);
    }

    public Node parse(String inputString) {
        Node              root    = new RootNode();
        List<Token.Entry> entries = tokenizer.tokenize(inputString);
        Lexer             lexer   = new DefaultLexer(entries);

        this.parser.parse(lexer, root, context);

        return root;
    }

}
