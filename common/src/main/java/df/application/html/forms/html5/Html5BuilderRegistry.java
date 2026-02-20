package df.application.html.forms.html5;

import df.application.dto.form.FieldDTO;
import df.application.dto.form.FormDTO;
import org.jmouse.common.dom.old_builder.AbstractBuilderRegistry;

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
