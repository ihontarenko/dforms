package df.base.model.user;

import df.base.jpa.Privilege;
import df.base.common.jpa.FieldSet;
import df.base.validation.constraint.ResourceExistence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import static df.base.common.jpa.FieldSet.Comparison.NOT_EQUAL;

@ResourceExistence.Set({
        // validation for new privilege
        @ResourceExistence(
                target = "name",
                fields = {@FieldSet(objectName = "name", entityName = "name")},
                entityClass = Privilege.class,
                message = "privilege name already taken (new)",
                existence = "id"
        ),
        // validation for existed privilege
        @ResourceExistence(
                target = "name",
                fields = {
                        @FieldSet(objectName = "id", entityName = "id", comparison = NOT_EQUAL),
                        @FieldSet(objectName = "name", entityName = "name")
                },
                entityClass = Privilege.class,
                invert = true,
                message = "privilege name already taken (upd)",
                existence = "id"
        ),
        // validation on passed id in request
        @ResourceExistence(
                target = "id",
                fields = {@FieldSet(objectName = "id", entityName = "id")},
                entityClass = Privilege.class,
                invert = true,
                unique = false,
                message = "the PRIVILEGE_ID must exist",
                existence = "id"
        )
})
public class PrivilegeDTO {

    private String id;

    @NotEmpty(message = "privilege name is required")
    @Size(max = 32)
    private String name;

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

}
