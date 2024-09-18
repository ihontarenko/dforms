package df.base.common.annotation.parsing;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.parser.Parser;
import df.base.common.libs.ast.parser.ParserContext;
import df.base.common.libs.ast.token.Token;
import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.annotation.parsing.ast.AnnotationNode;
import df.base.common.annotation.parsing.configurator.AnnotationParserConfigurator;
import df.base.common.annotation.parsing.configurator.AnnotationTokenizerConfigurator;
import df.base.common.annotation.parsing.parser.AnnotationParser;

import static df.base.common.annotation.parsing.AnnotationToken.T_ANNOTATION;

public class Demo {

    public static void main(String[] args) {
        ParserContext context   = ParserContext.CONTEXT;
        Tokenizer     tokenizer = new AnnotationTokenizer();

        new AnnotationTokenizerConfigurator().configure(tokenizer);
        new AnnotationParserConfigurator().configure(context);

        Parser parser = context.getParser(AnnotationParser.class);
        Lexer  lexer  = new AnnotationLexer(tokenizer.tokenize("@NotEmpty(message = \"form description is required\", size={@Size(max = 32)}, item=@Size(max = 32))"));

        // make sure we start parsing with the correct token
        parser.ensureCurrent(lexer, T_ANNOTATION);

        for (Token.Entry entry : lexer) {
            System.out.println(entry);
        }

        lexer.cursor(0);

        AnnotationNode root = new AnnotationNode(lexer.current());

        parser.parse(lexer, root, context);

        System.out.println(root);
    }

}
