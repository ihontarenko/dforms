package df.base.common.support;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
@SuppressWarnings({"unused"})
public class RoutePathGenerator {

    public static final String                       PLACEHOLDER = "{%s}";
    private final       RequestMappingHandlerMapping handlerMapping;

    private final Map<String, String> routeCache = new HashMap<>();

    public RoutePathGenerator(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @PostConstruct
    private void initialize() {
        handlerMapping.getHandlerMethods().forEach((mappingInfo, handlerMethod) -> {
            Class<?> controllerClass = handlerMethod.getBeanType();
            Method   method          = handlerMethod.getMethod();

            RequestMapping methodAnnotation     = method.getAnnotation(RequestMapping.class);
            RequestMapping controllerAnnotation = controllerClass.getAnnotation(RequestMapping.class);

            String basePath   = getRouteMapping(controllerAnnotation);
            String methodPath = getRouteMapping(methodAnnotation);
            String fullPath   = basePath + methodPath;
            String cacheKey   = getCacheKey(controllerClass, method.getName());

            routeCache.put(cacheKey, fullPath);
        });
    }

    public String getUrlFor(Class<?> controllerClass, String methodName, Map<String, Object> params) {
        String key = getCacheKey(controllerClass, methodName);
        String url = routeCache.get(key);

        if (url == null) {
            throw new IllegalArgumentException("ROUTE NOT FOUND FOR %s".formatted(key));
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            url = url.replace(PLACEHOLDER.formatted(entry.getKey()), entry.getValue().toString());
        }

        return url;
    }

    public String getRouteMapping(RequestMapping mapping) {
        return mapping != null && mapping.value().length > 0 ? mapping.value()[0] : "";
    }

    private String getCacheKey(Class<?> controllerClass, String methodName) {
        return controllerClass.getName() + "#" + methodName;
    }
}

