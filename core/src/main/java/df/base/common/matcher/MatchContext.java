package df.base.common.matcher;

import df.base.common.context.AbstractContext;
import df.base.common.context.Context;

/**
 * A context used during matching operations. It provides additional information
 * or state that can be used by matchers to make decisions.
 *
 * <p>The {@code MatchContext} can store contextual data that helps matchers
 * during their execution. It extends the basic {@link Context} interface.</p>
 *
 * @see Context
 */
public interface MatchContext extends Context {

    /**
     * Creates a default instance of {@code MatchContext}.
     *
     * <p>This method provides a simple, default implementation of the {@code MatchContext}
     * that can be used in most matching scenarios.</p>
     *
     * @return a default instance of {@code MatchContext}
     * @example
     * <pre>{@code
     * MatchContext context = MatchContext.createDefault();
     * }</pre>
     */
    static MatchContext createDefault() {
        return new Default();
    }

    /**
     * The default implementation of {@code MatchContext}.
     *
     * <p>This class extends {@link AbstractContext} and serves as the base context
     * for matching operations. It is used when no specific context implementation
     * is required.</p>
     */
    class Default extends AbstractContext implements MatchContext {

    }

}
