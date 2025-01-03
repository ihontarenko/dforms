package df.application.dto.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class FieldSetDTO extends HashSet<FieldDTO> {

    public FieldSetDTO() {
    }

    public FieldSetDTO(@NotNull Collection<? extends FieldDTO> collection) {
        super(collection);
    }

}
