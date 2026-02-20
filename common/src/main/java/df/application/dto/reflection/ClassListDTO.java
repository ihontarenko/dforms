package df.application.dto.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class ClassListDTO extends ArrayList<ClassDTO> {

    public ClassListDTO() {
    }

    public ClassListDTO(@NotNull Collection<? extends ClassDTO> collection) {
        super(collection);
    }

}
