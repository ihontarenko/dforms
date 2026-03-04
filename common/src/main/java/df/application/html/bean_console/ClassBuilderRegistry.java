package df.application.html.bean_console;

import df.application.dto.reflection.*;
import org.jmouse.dom.constructor.AbstractNodeConstructorRegistry;

public class ClassBuilderRegistry extends AbstractNodeConstructorRegistry {

    public ClassBuilderRegistry() {
        initialize();
    }

    @Override
    protected void initialize() {
        setConstructor(ClassDTO.class, new ClassTypeBuilder());
        setConstructor(ClassListDTO.class, new ClassSetBuilder());
        setConstructor(MethodDTO.class, new MethodItemBuilder());
        setConstructor(MethodListDTO.class, new MethodSetBuilder());
        setConstructor(FieldDTO.class, new FieldItemBuilder());
        setConstructor(FieldListDTO.class, new FieldSetBuilder());
    }

}
