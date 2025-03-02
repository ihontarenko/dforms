package df.application.dto.user;

import df.application.dto.DTO;
import df.application.persistence.entity.user.Privilege;
import df.common.support.jpa.JpaCriteria;
import df.common.validation.jakarta.Fields;
import df.common.validation.jakarta.constraint.JpaResource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import static df.common.validation.jakarta.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for new privilege
        @JpaResource(
                pointer = "name",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = Privilege.class,
                message = "privilege name already taken (new)",
                applier = "!#hasText(id)"
        ),
        // validation for existed privilege
        @JpaResource(
                pointer = "name",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL),
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = Privilege.class,
                message = "privilege name already taken (upd)",
                applier = "#hasText(id)"
        ),
        // validation on passed id in request
        @JpaResource(
                pointer = "id",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = Privilege.class,
                message = "the PRIVILEGE_ID must exist",
                applier = "#hasText(id)"
        )
})
public class PrivilegeDTO implements DTO {

    private String id;

    @NotEmpty(message = "privilege name is required")
    @Size(max = 32)
    private String name;

    public String id() {
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
