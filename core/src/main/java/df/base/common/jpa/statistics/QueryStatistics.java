package df.base.common.jpa.statistics;

import java.util.HashMap;
import java.util.Map;

public class QueryStatistics {

    private final Map<String, Long> queryExecutionTimes = new HashMap<>();
    private       long              queryCount          = 0;
    private       long              executionTime       = 0;

    public void addQueryExecutionTime(String query, long executionTime) {
        queryCount++;
        this.executionTime += executionTime;
        queryExecutionTimes.put(query, executionTime);
    }

    public long getQueryCount() {
        return queryCount;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public Map<String, Long> getQueryExecutionTimes() {
        return queryExecutionTimes;
    }

}
