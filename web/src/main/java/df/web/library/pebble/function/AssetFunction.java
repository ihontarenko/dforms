package df.web.library.pebble.function;

import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import io.pebbletemplates.spring.servlet.PebbleView;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssetFunction implements Function {

    public static final String FUNCTION_NAME = "asset";
    public static final String ARGUMENT_NAME = "path";

    private final ApplicationContext context;

    public AssetFunction(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object execute(Map<String, Object> arguments, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        String path = (String) arguments.get(ARGUMENT_NAME);

        path = path != null
                ? getResourceUrlProvider().getForLookupPath(path.startsWith("/") ? path : "/" + path) : "/UNDEFINED";

        return getHttpServletRequest(context).getContextPath() + path;
    }

    @Override
    public List<String> getArgumentNames() {
        return new ArrayList<>() {{
            add(ARGUMENT_NAME);
        }};
    }

    private HttpServletRequest getHttpServletRequest(EvaluationContext context) {
        return (HttpServletRequest) context.getVariable(PebbleView.REQUEST_VARIABLE_NAME);
    }

    private ResourceUrlProvider getResourceUrlProvider() {
        return context.getBean(ResourceUrlProvider.class);
    }

}
