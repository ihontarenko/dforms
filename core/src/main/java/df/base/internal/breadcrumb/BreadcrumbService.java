package df.base.internal.breadcrumb;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Service
public class BreadcrumbService {

    private final RequestMappingHandlerMapping mapping;

    public BreadcrumbService(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping mapping) {
        this.mapping = mapping;
    }

    @SuppressWarnings({"unchecked"})
    public List<Breadcrumb> getBreadcrumbsForRequest(HttpServletRequest request) {
        HandlerMethod handler;

        try {
            handler = (HandlerMethod) requireNonNull(mapping.getHandler(request)).getHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Breadcrumbs annotation = handler.getMethodAnnotation(Breadcrumbs.class);

        if (annotation != null) {
            Map<String, String> variables
                    = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            return Stream.of(annotation.value())
                    .map(breadcrumb -> resolveBreadcrumbItem(breadcrumb, variables)).toList();
        }
        return null;
    }

    private Breadcrumb resolveBreadcrumbItem(Breadcrumbs.Item item, Map<String, String> variables) {
        String url   = item.url();
        String label = item.label();

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            url = url.replace("{%s}".formatted(entry.getKey()), entry.getValue());
            label = label.replace("{%s}".formatted(entry.getKey()), entry.getValue());
        }

        return new Breadcrumb(label, url);
    }
}


