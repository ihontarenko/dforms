package df.base.common.parser.recognizer;

import df.base.common.parser.Priority;

import java.util.Optional;

public interface Recognizer<R, T> extends Priority {
    Optional<R> recognize(T subject);
}
