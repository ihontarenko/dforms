package df.base.common.jpa.statistics;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class QueryStatisticsHolder {

    private final QueryStatistics queryStatistics = new QueryStatistics();

    public QueryStatistics getQueryStatistics() {
        return queryStatistics;
    }

}
