package df.base.common.jbm.scanner.filter.type;

public class DeprecatedClassFilter extends ClassAnnotatedClassFilter {

    public DeprecatedClassFilter(boolean invert) {
        super(Deprecated.class, invert);
    }

    public DeprecatedClassFilter() {
        this(false);
    }

}
