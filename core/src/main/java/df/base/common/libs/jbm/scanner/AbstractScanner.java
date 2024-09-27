package df.base.common.libs.jbm.scanner;

import df.base.common.libs.jbm.filter.Filter;
import df.base.common.libs.jbm.filter.FilterAware;
import df.base.common.libs.jbm.filter.FilteringMode;
import df.base.common.matcher.reflection.ClassMatchers;
import df.base.common.matcher.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractScanner<T> implements Scanner<T>, CompositeScanner<T>, FilterAware<Filter<T>> {

    protected final List<Scanner<T>>   scanners     = new ArrayList<>();
    private final   List<Predicate<T>> filters      = new ArrayList<>();
    private final   Matcher<Class<?>>  classMatcher = ClassMatchers.isPublic();
    private         FilteringMode      mode         = FilteringMode.AND;

    @Override
    public void addScanner(Scanner<T> scanner) {
        scanners.add(scanner);
    }

    @Override
    public FilteringMode getFilteringMode() {
        return mode;
    }

    @Override
    public void setFilteringMode(FilteringMode mode) {
        this.mode = mode;
    }

    @Override
    public void addFilter(Filter<T> filter) {
        filters.add(filter);
    }

    @Override
    public void removeFilter(Class<? extends Filter<T>> filterClass) {
        filters.removeIf(predicate -> predicate.getClass().isAssignableFrom(filterClass));
    }

    @Override
    public void clearFilters() {
        filters.clear();
    }

    protected boolean doFilter(T target) {
        Stream<Predicate<T>> stream = filters.stream();
        return ((mode == FilteringMode.AND)
                ? stream.reduce(Predicate::and)
                : stream.reduce(Predicate::or)).orElse(object -> false).test(target);
    }

}
