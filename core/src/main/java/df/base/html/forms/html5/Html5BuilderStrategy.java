package df.base.html.forms.html5;

import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;
import df.base.common.elements.builder.AbstractBuilderStrategy;

public class Html5BuilderStrategy extends AbstractBuilderStrategy {

    public Html5BuilderStrategy() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(FormDTO.class, new FormBuilder());
        setBuilder(FieldDTO.class, new FieldBuilder());
        setBuilder(FieldAttributeDTO.class, new FieldBuilder());
    }

}
