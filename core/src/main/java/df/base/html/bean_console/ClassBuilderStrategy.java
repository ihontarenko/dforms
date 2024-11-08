package df.base.html.bean_console;

import df.base.common.elements.builder.AbstractBuilderStrategy;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.ClassListDTO;

public class ClassBuilderStrategy extends AbstractBuilderStrategy {

    public ClassBuilderStrategy() {
        initialize();
    }

    @Override
    protected void initialize() {
        setBuilder(ClassListDTO.class, new ClassListBuilder());
        setBuilder(ClassDTO.class, new ClassTypeBuilder());
    }

}
