package df.base.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class HibernateSQLLoggingAspect {

    private static final AtomicInteger queryCount = new AtomicInteger(0);

    // Перехоплення логування Hibernate SQL через SLF4J
    @After("execution(* org.slf4j.Logger.debug(String)) && target(logger)")
    public void interceptSQLLog(Logger logger) {
        if (logger.getName().contains("org.hibernate.SQL")) {
            queryCount.incrementAndGet();
        }
    }

    public int getQueryCount() {
        return queryCount.get();
    }

    public void reset() {
        queryCount.set(0);
    }
}

