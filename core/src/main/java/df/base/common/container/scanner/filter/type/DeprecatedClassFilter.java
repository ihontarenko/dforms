package df.base.common.container.scanner.filter.type;

public class DeprecatedClassFilter extends ClassAnnotatedClassFilter {

    public DeprecatedClassFilter(boolean invert) {
        super(Deprecated.class, invert);
    }

    public DeprecatedClassFilter() {
        this(false);
    }

}
