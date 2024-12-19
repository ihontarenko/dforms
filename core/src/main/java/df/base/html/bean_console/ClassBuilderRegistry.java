package df.base.html.bean_console;

import df.base.common.elements.builder.AbstractBuilderRegistry;
import df.base.dto.reflection.*;

public class ClassBuilderRegistry extends AbstractBuilderRegistry {

    public ClassBuilderRegistry() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(ClassDTO.class, new ClassTypeBuilder());
        setBuilder(ClassSetDTO.class, new ClassSetBuilder());
        setBuilder(MethodDTO.class, new MethodItemBuilder());
        setBuilder(MethodSetDTO.class, new MethodSetBuilder());
        setBuilder(FieldDTO.class, new FieldItemBuilder());
        setBuilder(FieldSetDTO.class, new FieldSetBuilder());
    }

}
