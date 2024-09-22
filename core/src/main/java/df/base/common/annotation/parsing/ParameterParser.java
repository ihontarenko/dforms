package df.base.common.annotation.parsing;

import df.base.common.annotation.parsing.configurator.AnnotationParserConfigurator;
import df.base.common.annotation.parsing.configurator.AnnotationTokenizerConfigurator;
import df.base.common.annotation.parsing.parser.AnnotationParameterParser;
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
    private final Tokenizer     tokenizer = new AnnotationTokenizer();
    private       Parser        parser;

    public ParameterParser() {
        initialize();
    }

    private void initialize() {
        new AnnotationParserConfigurator().configure(context);
        new AnnotationTokenizerConfigurator().configure(tokenizer);

        this.parser = context.getParser(AnnotationParameterParser.class);
    }

    public Node parse(String inputString) {
        Node              root    = new RootNode();
        List<Token.Entry> entries = tokenizer.tokenize(inputString);
        Lexer             lexer   = new AnnotationLexer(entries);

        this.parser.parse(lexer, root, context);

        return root;
    }

}
