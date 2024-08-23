package df.base.model.user;

import df.base.common.support.JpaCriteria;
import df.base.jpa.Role;
import df.base.validation.Fields;
import df.base.validation.constraint.JpaResource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import static df.base.validation.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for new roles
        @JpaResource(
                target = "name",
                fields = {
                        @Fields(objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = Role.class,
                message = "role name already taken (new)",
                applier = "!#hasText(id)"
        ),
        // validation for existed roles
        @JpaResource(
                target = "name",
                fields = {
                        @Fields(objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL),
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = Role.class,
                message = "role name already taken (upd)",
                applier = "#hasText(id)"
        ),
        // validation on passed id in request
        @JpaResource(
                target = "id",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = Role.class,
                message = "the ROLE_ID must exist",
                applier = "#hasText(id)"
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
