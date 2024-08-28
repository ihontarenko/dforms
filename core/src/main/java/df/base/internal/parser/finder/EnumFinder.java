package df.base.internal.parser.finder;

import df.base.internal.parser.token.Token;

import java.util.Optional;

public class EnumFinder<T extends Enum<T> & Token> implements Finder<T, String, T[]> {

    @Override
    public Optional<T> find(String value, T[] values) {
        Optional<T> token = Optional.empty();

        for (T current : values) {
            if (current.expressions().length > 0) {
                for (String example : current.expressions()) {
                    if (example.equalsIgnoreCase(value)) {
                        token = Optional.of(current);
                        break;
                    }
                }
            }
        }

        return token;
    }

}
