package df.base.common.scanner;

import df.base.common.filter.Filter;
import df.base.common.filter.FilterAware;
import df.base.common.filter.FilteringMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static df.base.common.filter.FilteringMode.AND;

public abstract class AbstractScanner<T> implements Scanner<T>, CompositeScanner<T>, FilterAware<Filter<T>> {

    protected final List<Scanner<T>>   scanners = new ArrayList<>();
    private final   List<Predicate<T>> filters  = new ArrayList<>();
    private         FilteringMode      mode     = AND;

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
        Stream<Predicate<T>>         stream      = filters.stream();
        BinaryOperator<Predicate<T>> accumulator = AND.equals(mode) ? Predicate::and : Predicate::or;

        return stream.reduce(accumulator).orElse(object -> false).test(target);
    }

}
