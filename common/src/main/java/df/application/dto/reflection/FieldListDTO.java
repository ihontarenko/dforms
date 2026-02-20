package df.application.dto.reflection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class FieldListDTO extends ArrayList<FieldDTO> {

    public FieldListDTO() {
    }

    public FieldListDTO(@NotNull Collection<? extends FieldDTO> collection) {
        super(collection);
    }

}
