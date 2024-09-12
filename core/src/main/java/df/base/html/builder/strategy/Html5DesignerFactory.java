package df.base.html.builder.strategy;

import df.base.common.elements.builder.ElementBuilderFactory;
import df.base.html.builder.strategy.html5.FieldDesigner;
import df.base.dto.form.FieldDTO;

public class Html5DesignerFactory extends ElementBuilderFactory {

    @Override
    protected void initialize() {
        addBuilder(FieldDTO.class, new FieldDesigner());
    }

}
