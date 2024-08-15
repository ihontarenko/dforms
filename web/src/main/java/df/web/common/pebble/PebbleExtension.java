package df.web.common.pebble;

import df.web.common.pebble.function.IfActivePageFunction;
import df.web.common.pebble.function.AssetFunction;
import df.web.common.pebble.function.IfFieldErrors;
import df.web.common.pebble.function.TranslateFunction;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class PebbleExtension extends AbstractExtension {

    private final ApplicationContext context;
    private final HttpServletRequest request;

    public PebbleExtension(ApplicationContext context, HttpServletRequest request) {
        this.context = context;
        this.request = request;
    }

    @Override
    public Map<String, Function> getFunctions() {
        Function translation = new TranslateFunction(context);

        return new HashMap<>() {{
            put(AssetFunction.FUNCTION_NAME, new AssetFunction(context));
            put(TranslateFunction.FUNCTION_NAME, translation);
            put(TranslateFunction.FUNCTION_NAME_SHORT, translation);
            put(IfFieldErrors.FUNCTION_NAME, new IfFieldErrors());
            put(IfActivePageFunction.FUNCTION_NAME, new IfActivePageFunction(request));
        }};
    }

}
