package df.base.internal.parser.finder;

import java.util.Optional;

public interface Finder<R, V, C> {
    Optional<R> find(V value, C context);
}
