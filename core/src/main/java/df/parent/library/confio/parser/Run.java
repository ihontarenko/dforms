package df.parent.library.confio.parser;

import df.parent.library.parser.lexer.Lexer;
import df.parent.library.parser.token.DefaultTokenizer;
import df.parent.library.parser.token.Token;
import df.parent.library.parser.token.Tokenizer;
import df.parent.utils.ResourceReader;

public class Run {

    public static void main(String... arguments) {
        Tokenizer tokenizer = new DefaultTokenizer();
        String    content   = ResourceReader.readFileToString("default.confio");

        new ConfioTokenizerSetup().configure(tokenizer);

        Lexer lexer = new ConfioLexer(tokenizer.tokenize(content));

        for (Token.Entry entry : lexer) {
            System.out.println(entry);
        }

    }

}
