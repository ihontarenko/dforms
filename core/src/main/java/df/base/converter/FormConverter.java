package df.base.converter;

import df.base.jpa.form.Form;
import df.base.model.form.FormDTO;

public class FormConverter implements Converter<Form, FormDTO> {

    @Override
    public FormDTO convert(Form source) {
        FormDTO formDTO = new FormDTO();

        formDTO.setId(source.getId());
        formDTO.setName(source.getName());
        formDTO.setDescription(source.getDescription());
        formDTO.setStatus(source.getStatus());
        formDTO.setOwnerId(source.getUser().getId());

        return formDTO;
    }

    @Override
    public Form reverse(FormDTO source) {
        Form form = new Form();

        form.setName(source.getName());
        form.setDescription(source.getDescription());
        form.setStatus(source.getStatus());

        return form;
    }

}
