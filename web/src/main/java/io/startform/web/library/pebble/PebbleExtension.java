package io.startform.web.library.pebble;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import io.startform.web.library.pebble.function.AssetFunction;

import java.util.HashMap;
import java.util.Map;

public class PebbleExtension extends AbstractExtension {

    @Override
    public Map<String, Function> getFunctions() {
        return new HashMap<>() {{
            put(AssetFunction.FUNCTION_NAME, new AssetFunction());
        }};
    }

}
