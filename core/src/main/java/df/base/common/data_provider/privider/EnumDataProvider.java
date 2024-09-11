package df.base.common.data_provider.privider;

import df.base.common.data_provider.DataProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EnumDataProvider<E extends Enum<E>> implements DataProvider<String, Object> {

    private final Class<E>            enumClass;
    private final Function<E, String> valueExtractor;

    public EnumDataProvider(Class<E> enumClass, Function<E, String> valueExtractor) {
        this.enumClass = enumClass;
        this.valueExtractor = valueExtractor;
    }

    @Override
    public Map<String, Object> getKeyValue() {
        Map<String, Object> keyValue = new HashMap<>();

        for (E enumConstant : enumClass.getEnumConstants()) {
            keyValue.put(enumConstant.name(), valueExtractor.apply(enumConstant));
        }

        return keyValue;
    }

}
