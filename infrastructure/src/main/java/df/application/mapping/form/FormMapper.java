package df.application.mapping.form;

import df.application.dto.form.FormDTO;
import df.application.persistence.entity.form.Form;
import org.jmouse.common.mapping.Mapper;
import org.springframework.stereotype.Service;

@Service
public class FormMapper implements Mapper<Form, FormDTO> {

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
        destination.setId(source.getId());
        destination.setOwnerId(source.getUser().getId());
        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setStatus(source.getStatus());
    }

    @Override
    public void reverse(FormDTO source, Form destination) {
        destination.setName(source.getName());
        destination.setDescription(source.getDescription());
        destination.setStatus(source.getStatus());
    }

}
