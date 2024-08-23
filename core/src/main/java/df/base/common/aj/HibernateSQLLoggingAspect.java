package df.base.common.aj;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings({"unused"})
public class HibernateSQLLoggingAspect {

    private static final AtomicInteger counter = new AtomicInteger(0);

    @After("execution(* org.slf4j.Logger.debug(String, Object..)) && args(message, ..) && target(logger)")
    public void interceptSQLLog(Logger logger, String message) {
        if (logger.getName().contains("org.hibernate.SQL")) {
            counter.incrementAndGet();
        }
    }

    public int getQueryCount() {
        return counter.get();
    }

    public void reset() {
        counter.set(0);
    }

}
