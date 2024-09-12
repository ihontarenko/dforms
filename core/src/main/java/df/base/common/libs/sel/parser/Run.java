package df.base.common.libs.sel.parser;

import df.base.common.libs.ast.token.Tokenizer;
import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.token.DefaultTokenizer;
import df.base.common.libs.ast.token.Token;
import df.base.common.resources.ResourceReader;

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
