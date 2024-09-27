package df.base.common.matcher;

import df.base.common.context.AbstractContext;
import df.base.common.context.Context;

public interface MatchContext extends Context {

    static MatchContext createDefault() {
        return new Default();
    }

    class Default extends AbstractContext implements MatchContext {

    }

}
