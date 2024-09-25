package df.base.common.proxy.cglib;

import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.core.Predicate;

public class SimpleNamingPolicy implements NamingPolicy {

    public static final String DEFAULT_PREFIX = SimpleNamingPolicy.class.getPackageName() + ".Object";
    public static final String DEFAULT_LABEL  = "$$SIMPLE_PROXY_CGLIB$$";

    @Override
    public String getClassName(String prefix, String source, Object key, Predicate names) {
        if (prefix == null) {
            prefix = DEFAULT_PREFIX;
        } else if (prefix.startsWith("java.") || prefix.startsWith("javax.")) {
            prefix = "_" + prefix;
        }

        String base;

        int existingLabel = prefix.indexOf(DEFAULT_LABEL);

        if (existingLabel >= 0) {
            base = prefix.substring(0, existingLabel + DEFAULT_LABEL.length());
        } else {
            base = prefix + DEFAULT_LABEL;
        }

        int    index   = 0;
        String attempt = base + index;

        while (names.evaluate(attempt)) {
            attempt = base + index++;
        }

        return attempt;
    }

}
