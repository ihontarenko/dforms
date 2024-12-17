package df.base.html.bean_console;

import df.base.common.elements.builder.AbstractBuilderStrategy;
import df.base.dto.reflection.*;

public class ClassBuilderStrategy extends AbstractBuilderStrategy {

    public ClassBuilderStrategy() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(ClassDTO.class, new ClassTypeBuilder());
        setBuilder(ClassSetDTO.class, new ClassListBuilder());
        setBuilder(MethodDTO.class, new MethodItemBuilder());
        setBuilder(MethodSetDTO.class, new MethodSetBuilder());
        setBuilder(FieldDTO.class, new FieldItemBuilder());
        setBuilder(FieldSetDTO.class, new FieldSetBuilder());
    }

}
