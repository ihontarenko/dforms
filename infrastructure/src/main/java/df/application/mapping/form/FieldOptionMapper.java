package df.application.mapping.form;

import df.common.mapping.Mapper;
import df.application.dto.form.FieldOptionDTO;
import df.application.persistence.entity.form.FieldOption;
import org.springframework.stereotype.Service;

@Service
public class FieldOptionMapper implements Mapper<FieldOption, FieldOptionDTO> {

    @Override
    public FieldOptionDTO map(FieldOption source) {
        FieldOptionDTO configDTO = new FieldOptionDTO();

        map(source, configDTO);

        return configDTO;
    }

    @Override
    public FieldOption reverse(FieldOptionDTO source) {
        FieldOption entity = new FieldOption();

        reverse(source, entity);

        return entity;
    }

    @Override
    public void map(FieldOption source, FieldOptionDTO destination) {
        destination.setId(source.getId());
        destination.setKey(source.getOptionValue());
        destination.setValue(source.getOptionLabel());
        destination.setFieldId(source.getField().getId());
    }

    @Override
    public void reverse(FieldOptionDTO source, FieldOption destination) {
        destination.setOptionValue(source.getKey());
        destination.setOptionLabel(source.getValue());
    }

}
