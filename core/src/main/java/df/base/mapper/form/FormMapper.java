package df.base.mapper.form;

import df.base.internal.Mapper;
import df.base.jpa.form.Form;
import df.base.model.form.FormDTO;

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
