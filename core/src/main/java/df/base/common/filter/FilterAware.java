package df.base.common.filter;

public interface FilterAware<F extends Filter<?>> {

    void setFilteringMode(FilteringMode mode);

    FilteringMode getFilteringMode();

    void addFilter(F filter);

    void removeFilter(Class<? extends F> filterClass);

    void clearFilters();

}
