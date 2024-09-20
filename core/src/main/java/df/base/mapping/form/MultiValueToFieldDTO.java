package df.base.mapping.form;

import df.base.common.support.Mapper;
import df.base.dto.form.FieldDTO;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class MultiValueToFieldDTO implements Mapper<MultiValueMap<String, String>, List<FieldDTO>> {

    @Override
    public List<FieldDTO> map(MultiValueMap<String, String> source) {
        return null;
    }

    @Override
    public MultiValueMap<String, String> reverse(List<FieldDTO> source) {
        return null;
    }

}
