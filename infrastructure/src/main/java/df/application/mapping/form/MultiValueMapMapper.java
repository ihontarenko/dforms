package df.application.mapping.form;

import org.jmouse.common.mapping.Mapper;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class MultiValueMapMapper implements Mapper<MultiValueMap<String, String>, Map<String, Object>> {

    @Override
    public Map<String, Object> map(MultiValueMap<String, String> source) {
        Map<String, Object> destination = new HashMap<>();

        source.forEach((key, strings) -> {
            Object value = strings;

            if (strings.size() == 1) {
                value = strings.get(0);
            }

            destination.put(key, value);
        });

        return destination;
    }

    @Override
    public MultiValueMap<String, String> reverse(Map<String, Object> source) {
        throw new UnsupportedOperationException();
    }

}
