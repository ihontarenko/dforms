package df.base.html.forms.bootstrap;

import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;
import df.base.common.elements.builder.AbstractBuilderStrategy;

public class BootstrapBuilderStrategy extends AbstractBuilderStrategy {

    public BootstrapBuilderStrategy() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(FormDTO.class, new FormBuilder());
        setBuilder(FieldDTO.class, new FieldBuilder());
    }

}
