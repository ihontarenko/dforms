package df.base.dto.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class MethodSetDTO extends HashSet<MethodDTO> {

    public MethodSetDTO() {
    }

    public MethodSetDTO(@NotNull Collection<? extends MethodDTO> collection) {
        super(collection);
    }

}
