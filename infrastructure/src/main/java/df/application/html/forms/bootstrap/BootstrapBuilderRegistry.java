package df.application.html.forms.bootstrap;

import df.application.dto.form.FieldDTO;
import df.application.dto.form.FormDTO;
import df.common.elements.builder.AbstractBuilderRegistry;

public class BootstrapBuilderRegistry extends AbstractBuilderRegistry {

    public BootstrapBuilderRegistry() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(FormDTO.class, new FormBuilder());
        setBuilder(FieldDTO.class, new FieldBuilder());
    }

}
