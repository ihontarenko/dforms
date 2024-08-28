package df.base.internal.parser.recognizer;

import df.base.internal.parser.token.Token;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PatternTokenRecognizer extends PredicateSupplierRecognizer<Token, String> {

    private final Token token;
    private final String expression;

    public PatternTokenRecognizer(String expression, Token token, int priority) {
        super(priority);
        this.expression = expression;
        this.token = token;
    }

    @Override
    public Supplier<Optional<Token>> getSupplier() {
        return () -> Optional.ofNullable(token);
    }

    @Override
    public Predicate<String> getPredicate() {
        return input -> input.matches(expression);
    }

}
