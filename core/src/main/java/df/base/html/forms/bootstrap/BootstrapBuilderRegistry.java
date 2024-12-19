package df.base.html.forms.bootstrap;

import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;
import df.base.common.elements.builder.AbstractBuilderRegistry;

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
