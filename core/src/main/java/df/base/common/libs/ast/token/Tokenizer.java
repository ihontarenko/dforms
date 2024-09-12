package df.base.common.libs.ast.token;

import df.base.common.libs.ast.Pattern;
import df.base.common.libs.ast.recognizer.Recognizer;

import java.util.List;

public interface Tokenizer {

    List<Token.Entry> tokenize(CharSequence sequence);

    void addPattern(Pattern<String> expression);

    void addRecognizer(Recognizer<Token, String> recognizer);

    default Token.Entry entry(Token token, String value, final int position, final int ordinal) {
        return Token.Entry.of(token, value, position, ordinal);
    }

}
