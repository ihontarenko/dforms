package df.base.common.libs.jbm.scanner.filter.type;

public class RegexClassFilter extends AbstractTypeFilter {

    private final String regex;

    public RegexClassFilter(String regex) {
        this(regex, true);
    }

    public RegexClassFilter(String regex, boolean invert) {
        super(invert);
        this.regex = regex;
    }

    @Override
    public boolean accept(Class<?> clazz) {
        return invert() != clazz.getName().matches(regex);
    }

}