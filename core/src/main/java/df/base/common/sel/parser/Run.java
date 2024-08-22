package df.base.common.sel.parser;

import df.base.common.parser.lexer.Lexer;
import df.base.common.parser.token.DefaultTokenizer;
import df.base.common.parser.token.Token;
import df.base.common.parser.token.Tokenizer;
import df.base.utils.ResourceReader;

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
