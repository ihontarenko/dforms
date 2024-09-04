package df.base.common.libs.parser.recognizer;

import df.base.common.libs.parser.Priority;

import java.util.Optional;

public interface Recognizer<R, T> extends Priority {
    Optional<R> recognize(T subject);
}
