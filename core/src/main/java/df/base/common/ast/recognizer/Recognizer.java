package df.base.common.ast.recognizer;

import df.base.common.ast.Priority;

import java.util.Optional;

public interface Recognizer<R, T> extends Priority {
    Optional<R> recognize(T subject);
}
