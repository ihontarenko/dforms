package df.base.model.user;

import df.base.jpa.Privilege;
import df.base.validation.Fields;
import df.base.validation.constraint.JpaResource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import static df.base.validation.Fields.Comparison.NOT_EQUAL;

@JpaResource.List({
        // validation for new privilege
        @JpaResource(
                target = "name",
                fields = {@Fields(objectField = "name", entityField = "name")},
                entityClass = Privilege.class,
                message = "privilege name already taken (new)",
                applier = "id"
        ),
        // validation for existed privilege
        @JpaResource(
                target = "name",
                fields = {
                        @Fields(objectField = "id", entityField = "id", comparison = NOT_EQUAL),
                        @Fields(objectField = "name", entityField = "name")
                },
                entityClass = Privilege.class,
                invert = true,
                message = "privilege name already taken (upd)",
                applier = "id"
        ),
        // validation on passed id in request
        @JpaResource(
                target = "id",
                fields = {@Fields(objectField = "id", entityField = "id")},
                entityClass = Privilege.class,
                invert = true,
                unique = false,
                message = "the PRIVILEGE_ID must exist",
                applier = "id"
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
