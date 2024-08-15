package df.web.common.pebble.function;

import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class IfActivePageFunction implements Function {

    public final static  String             FUNCTION_NAME           = "ifActivePage";
    private final static String             ARGUMENT_PATTERN        = "pattern";
    private final static String             ARGUMENT_INACTIVE_CLASS = "inactiveClass";
    private final static String             ARGUMENT_ACTIVE_CLASS   = "activeClass";
    private final static String             KEYWORD_ENDS            = "ends:";
    private final static String             KEYWORD_STARTS          = "starts:";
    private final static String             KEYWORD_CONTAINS        = "contains:";
    private final        HttpServletRequest request;

    public IfActivePageFunction(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of(ARGUMENT_PATTERN, ARGUMENT_ACTIVE_CLASS, ARGUMENT_INACTIVE_CLASS);
    }

    @Override
    public Object execute(Map<String, Object> arguments, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        String  pattern       = (String) arguments.get(ARGUMENT_PATTERN);
        String  activeClass   = (String) arguments.get(ARGUMENT_ACTIVE_CLASS);
        String  inactiveClass = arguments.get(ARGUMENT_INACTIVE_CLASS) != null ? (String) arguments.get(ARGUMENT_INACTIVE_CLASS) : "";
        String  currentUri    = request.getRequestURI();
        boolean isActive      = false;

        if (pattern.startsWith(KEYWORD_STARTS)) {
            String path = pattern.substring(KEYWORD_STARTS.length());
            isActive = currentUri.startsWith(path);
        } else if (pattern.startsWith(KEYWORD_ENDS)) {
            String path = pattern.substring(KEYWORD_ENDS.length());
            isActive = currentUri.endsWith(path);
        } else if (pattern.startsWith(KEYWORD_CONTAINS)) {
            String path = pattern.substring(KEYWORD_CONTAINS.length());
            isActive = currentUri.contains(path);
        } else {
            isActive = currentUri.equals(pattern);
        }

        return isActive ? activeClass : inactiveClass;
    }

}
