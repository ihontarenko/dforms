package df.base.dto.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class ClassSetDTO extends HashSet<ClassDTO> {

    public ClassSetDTO() {
    }

    public ClassSetDTO(@NotNull Collection<? extends ClassDTO> collection) {
        super(collection);
    }

}
