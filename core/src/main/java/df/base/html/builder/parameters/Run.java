package df.base.html.builder.parameters;

import df.base.common.libs.ast.lexer.Lexer;
import df.base.common.libs.ast.token.DefaultTokenizer;
import df.base.common.libs.ast.token.Token;
import df.base.common.libs.ast.token.Tokenizer;

public class Run {

    public static void main(String... arguments) {
        Tokenizer tokenizer = new DefaultTokenizer();
        String    content   = "@Config(action)";

        new TokenizerSetup().configure(tokenizer);

        Lexer lexer = new ParameterLexer(tokenizer.tokenize(content));

        for (Token.Entry entry : lexer) {
            System.out.println(entry);
            System.out.println(entry.token());
            System.out.println("---");
        }

    }

}
