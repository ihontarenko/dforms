package df.base.common.libs.confio.parser;

import df.base.common.libs.parser.lexer.Lexer;
import df.base.common.libs.parser.token.DefaultTokenizer;
import df.base.common.libs.parser.token.Token;
import df.base.common.libs.parser.token.Tokenizer;
import df.base.common.resources.ResourceReader;

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
