package df.base.model.user;

import df.base.jpa.Role;
import df.base.validation.Unique;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Unique.Set({
        @Unique(
                target = "name",
                fields = {@Unique.Field(objectName = "name", entityName = "name")},
                entityClass = Role.class,
                message = "role name already taken",
                existence = "id"
        ),
        @Unique(
                target = "id",
                fields = {@Unique.Field(objectName = "id", entityName = "id")},
                entityClass = Role.class,
                reverse = true,
                unique = false,
                message = "the ROLE_ID must exist",
                existence = "id"
        )
})
public class RoleDTO {

    public static final String ROLE_PREFIX = "ROLE_";

    private String id;

    @NotEmpty(message = "role name is required")
    @Size(max = 32)
    @Pattern(regexp = "[0-9A-Z_]+")
    private String name;

    @NotEmpty(message = "select at least one privilege")
    private List<String> privileges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

}
