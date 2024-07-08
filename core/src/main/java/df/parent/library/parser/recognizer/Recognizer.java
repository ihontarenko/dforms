package df.parent.library.parser.recognizer;

import df.parent.library.parser.Priority;

import java.util.Optional;

public interface Recognizer<R, T> extends Priority {
    Optional<R> recognize(T subject);
}
