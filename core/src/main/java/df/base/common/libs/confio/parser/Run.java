package df.base.common.libs.confio.parser;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.token.DefaultTokenizer;
import df.base.common.libs.ast.token.Token;
import df.base.common.libs.ast.token.Tokenizer;
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
