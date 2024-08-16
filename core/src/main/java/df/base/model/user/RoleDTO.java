package df.base.model.user;

import df.base.jpa.Role;
import df.base.common.jpa.FieldSet;
import df.base.validation.constrain.ResourceExistence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import static df.base.common.jpa.FieldSet.Comparison.NOT_EQUAL;

@ResourceExistence.Set({
        // validation for new roles
        @ResourceExistence(
                target = "name",
                fields = {@FieldSet(objectName = "name", entityName = "name")},
                entityClass = Role.class,
                message = "role name already taken (new)",
                existence = "id"
        ),
        // validation for existed roles
        @ResourceExistence(
                target = "name",
                fields = {
                        @FieldSet(objectName = "id", entityName = "id", comparison = NOT_EQUAL),
                        @FieldSet(objectName = "name", entityName = "name")
                },
                entityClass = Role.class,
                reverse = true,
                message = "role name already taken (upd)",
                existence = "id"
        ),
        // validation on passed id in request
        @ResourceExistence(
                target = "id",
                fields = {@FieldSet(objectName = "id", entityName = "id")},
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
