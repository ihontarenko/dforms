package df.application.dto.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class MethodListDTO extends ArrayList<MethodDTO> {

    public MethodListDTO() {
    }

    public MethodListDTO(@NotNull Collection<? extends MethodDTO> collection) {
        super(collection);
    }

}
