package df.base.internal.parser.recognizer;

import df.base.internal.parser.Priority;

import java.util.Optional;

public interface Recognizer<R, T> extends Priority {
    Optional<R> recognize(T subject);
}
