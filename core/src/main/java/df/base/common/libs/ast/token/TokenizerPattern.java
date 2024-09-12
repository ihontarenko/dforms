package df.base.common.libs.ast.token;

import df.base.common.libs.ast.Pattern;

import static java.util.regex.Pattern.compile;

public class TokenizerPattern implements Pattern<String> {

    protected final int                     priority;
    protected final java.util.regex.Pattern pattern;

    public TokenizerPattern(String expression, int priority) {
        this.pattern = compile(expression);
        this.priority = priority;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public String pattern() {
        return pattern.pattern();
    }

}
