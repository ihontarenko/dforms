package df.base.common.application_context.scanner.filter.type;

abstract public class AbstractTypeFilter implements TypeFilter {

    private final boolean invert;

    public AbstractTypeFilter(boolean invert) {
        this.invert = invert;
    }

    public boolean invert() {
        return invert;
    }

}
