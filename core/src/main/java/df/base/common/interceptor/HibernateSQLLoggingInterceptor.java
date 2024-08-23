package df.base.common.interceptor;

import df.base.common.aj.HibernateSQLLoggingAspect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class HibernateSQLLoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSQLLoggingAspect.class);

    private final HibernateSQLLoggingAspect loggingAspect;

    public HibernateSQLLoggingInterceptor(HibernateSQLLoggingAspect loggingAspect) {
        this.loggingAspect = loggingAspect;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception exception) {
        LOGGER.info("QUERY COUNT: {}; HTTP RESPONSE STATUS: {}", loggingAspect.getQueryCount(), response.getStatus());
        if (response.getStatus() != HttpServletResponse.SC_FOUND) {
            loggingAspect.reset();
        }
    }

}
