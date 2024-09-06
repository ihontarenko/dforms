package df.base.mapping.form;

import df.base.common.Mapper;
import df.base.dto.form.FieldOptionDTO;
import df.base.persistence.entity.form.FieldOption;
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
        destination.setOptionValue(source.getOptionValue());
        destination.setOptionLabel(source.getOptionLabel());
        destination.setFieldId(source.getField().getId());
    }

    @Override
    public void reverse(FieldOptionDTO source, FieldOption destination) {
        destination.setOptionValue(source.getOptionValue());
        destination.setOptionLabel(source.getOptionLabel());
    }

}
