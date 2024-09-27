package df.base.html.builder.bootstrap;

import df.base.dto.form.FieldDTO;
import df.base.dto.form.FormDTO;
import df.base.html.builder.AbstractBuilderStrategy;

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
