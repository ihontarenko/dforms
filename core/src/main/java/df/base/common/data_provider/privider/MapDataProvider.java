package df.base.common.data_provider.privider;

import df.base.common.data_provider.DataProvider;

import java.util.HashMap;
import java.util.Map;

public class MapDataProvider implements DataProvider<String, Object> {

    private final Map<String, Object> keyValue = new HashMap<>();

    public MapDataProvider(Map<String, Object> keyValue) {
        this.keyValue.putAll(keyValue);
    }

    @Override
    public Map<String, Object> getKeyValue() {
        return this.keyValue;
    }

}
