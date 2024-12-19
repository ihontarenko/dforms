package df.base.html.forms.html5;

import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;
import df.base.common.elements.builder.AbstractBuilderRegistry;

public class Html5BuilderRegistry extends AbstractBuilderRegistry {

    public Html5BuilderRegistry() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(FormDTO.class, new FormBuilder());
        setBuilder(FieldDTO.class, new FieldBuilder());
    }

}
