package df.base.internal.sel.parser;

import df.base.internal.parser.lexer.Lexer;
import df.base.internal.parser.token.DefaultTokenizer;
import df.base.internal.parser.token.Token;
import df.base.internal.parser.token.Tokenizer;
import df.base.utils.ResourceReader;

import java.util.List;

public class Run {

    public static void main(String... arguments) {
        Tokenizer tokenizer = new DefaultTokenizer();
        String    content   = ResourceReader.readFileToString("sel.expression");

        new SELTokenizerSetup().configure(tokenizer);

        Lexer lexer = new SELLexer(tokenizer.tokenize(content));

        for (Token.Entry entry : lexer) {
            System.out.println(entry);
            System.out.println(entry.token());
            System.out.println("---");
        }

    }

}
