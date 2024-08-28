package df.base.internal.confio.parser;

import df.base.internal.parser.lexer.Lexer;
import df.base.internal.parser.token.DefaultTokenizer;
import df.base.internal.parser.token.Token;
import df.base.internal.parser.token.Tokenizer;
import df.base.utils.ResourceReader;

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
