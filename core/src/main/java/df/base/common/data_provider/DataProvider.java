package df.base.common.data_provider;

import java.util.Map;

public interface DataProvider<K, V> {
    Map<K, V> getKeyValue();
}
