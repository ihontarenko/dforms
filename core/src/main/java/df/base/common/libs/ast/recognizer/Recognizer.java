package df.base.common.libs.ast.recognizer;

import df.base.common.libs.ast.Priority;

import java.util.Optional;

public interface Recognizer<R, T> extends Priority {
    Optional<R> recognize(T subject);
}
