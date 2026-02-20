package df.application.old_mapping.form;

import df.application.dto.form.FormDTO;
import org.jmouse.common.mapping.Mapper;
import df.application.persistence.entity.form.Form;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class DeepFormMapper implements Mapper<Form, FormDTO> {

    @Override
    public FormDTO map(Form source) {
        FormDTO formDTO = new FormDTO();

        map(source, formDTO);

        return formDTO;
    }

    @Override
    public Form reverse(FormDTO source) {
        Form form = new Form();

        reverse(source, form);

        return form;
    }

    @Override
    public void map(Form source, FormDTO destination) {
        new FormMapper().map(source, destination);
        ofNullable(source.getFields()).ifPresent(fields -> fields.stream().map(new DeepFieldMapper()::map)
                .forEach(destination::addField));
    }

    @Override
    public void reverse(FormDTO source, Form destination) {
        new FormMapper().reverse(source, destination);
        ofNullable(source.getFields()).ifPresent(fields -> fields.stream().map(new DeepFieldMapper()::reverse)
                .forEach(destination::addField));
    }

}
