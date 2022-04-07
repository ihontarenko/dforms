package io.startform.web.library.pebble.function;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.spring.PebbleView;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.startform.parent.library.BeansHolder.INSTANCE;

public class AssetFunction implements Function {

    public static final String FUNCTION_NAME = "asset";
    public static final String ARGUMENT_NAME = "path";

    @Override
    public Object execute(Map<String, Object> arguments, PebbleTemplate self,
                          EvaluationContext context, int lineNumber) {
        String path = (String) arguments.get(ARGUMENT_NAME);

        if (path != null) {
            path = getResourceUrlProvider().getForLookupPath(path.startsWith("/") ? path : "/" + path);
        } else {
            path = "/UNDEFINED";
        }

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
        return INSTANCE.get(ApplicationContext.class)
                .getBean(ResourceUrlProvider.class);
    }

}
