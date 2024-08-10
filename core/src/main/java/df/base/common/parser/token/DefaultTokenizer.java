package df.base.common.parser.token;

import df.base.common.parser.Pattern;
import df.base.common.parser.PriorityComparator;
import df.base.common.parser.recognizer.Recognizer;
import df.base.common.parser.token.Token.Entry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

public class DefaultTokenizer implements Tokenizer {

    private static final int                             COMPILER_FLAGS = CASE_INSENSITIVE;
    private final        List<Pattern<String>>           expressions;
    private final        List<Recognizer<Token, String>> recognizers;

    public DefaultTokenizer() {
        this.expressions = new ArrayList<>();
        this.recognizers = new ArrayList<>();
    }

    @Override
    public List<Entry> tokenize(CharSequence sequence) {
        ensureExpressions();
        ensureRecognizers();

        var         collector  = Collectors.joining("|");
        List<Entry> tokens     = new ArrayList<>();
        String      expression = "(" + expressions.stream().map(Pattern::pattern).collect(collector) + ")";
        Matcher     matcher    = compile(expression, COMPILER_FLAGS).matcher(sequence);
        int         ordinal    = 0;

        while (matcher.find()) {
            String                              value    = matcher.group(1);
            int                                 position = matcher.start() + 1;
            Optional<Token>                     token    = Optional.empty();
            Iterator<Recognizer<Token, String>> iterator = recognizers.iterator();

            while (iterator.hasNext() && token.isEmpty()) {
                token = iterator.next().recognize(value);
            }

            if (token.isEmpty()) {
                throw new TokenizerException(String.format("CAN NOT RECOGNIZE TOKEN [%s] TYPE", value));
            }

            if (token.get().equals(DefaultToken.T_STRING)) {
                value = value.substring(1, value.length() - 1);
            }

            tokens.add(entry(token.get(), value, position, ++ordinal));
        }

        return tokens;
    }

    @Override
    public void addPattern(Pattern<String> expression) {
        this.expressions.add(expression);
    }

    @Override
    public void addRecognizer(Recognizer<Token, String> recognizer) {
        this.recognizers.add(recognizer);
    }

    private void ensureExpressions() {
        if (expressions.isEmpty()) {
            throw new TokenizerException("NO EXPRESSIONS CONFIGURED");
        } else {
            expressions.sort(new PriorityComparator<>());
        }
    }

    private void ensureRecognizers() {
        if (expressions.isEmpty()) {
            throw new TokenizerException("NO RECOGNIZERS CONFIGURED");
        } else {
            recognizers.sort(new PriorityComparator<>());
        }
    }

}
