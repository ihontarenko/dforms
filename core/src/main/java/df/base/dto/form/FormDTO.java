package df.base.dto.form;

import df.base.common.support.jpa.JpaCriteria;
import df.base.common.validation.jakarta.Fields;
import df.base.dto.DTO;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.support.FormStatus;
import df.base.common.validation.jakarta.Fields.Value;
import df.base.common.validation.jakarta.constraint.EnumPattern;
import df.base.common.validation.jakarta.constraint.JpaResource;
import df.base.common.validation.jakarta.constraint.SpelConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static df.base.common.validation.jakarta.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for creating new form
        @JpaResource(
                pointer = "name",
                fields = {
                        @Fields(
                                objectValue = @Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = Form.class,
                // message = "[NEW]: form name already taken",
                applier = "!#hasText(id)" // for new records
        ),
        // validation for updating existing form
        @JpaResource(
                pointer = "name",
                fields = {
                        @Fields(
                                objectValue = @Value(value = "name", type = FIELD_NAME),
                                entityField = "name"),
                        @Fields(
                                objectValue = @Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL)
                },
                entityClass = Form.class,
                message = "[UPD]: form name already taken",
                applier = "#hasText(id)" // for existing records
        ),
        // check if request form id is correct
        @JpaResource(
                pointer = "id",
                fields = {
                        @Fields(
                                objectValue = @Value(value = "id", type = FIELD_NAME),
                                entityField = "id")
                },
                entityClass = Form.class,
                message = "the FORM_ID must exist",
                applier = "#hasText(id)",
                predicate = "!#result.empty"
        ),
})
@SpelConstraint.List(value = {
        @SpelConstraint(
                pointer = "id",
                applier = "#hasText(id) && #noRole('SUPER_USER')",
                value = "@formRepository.existsByIdAndUser(id, #getAuthorizedUser())",
                message = "no-no-no... you must be owner or super-user to modify other people's forms"),
        @SpelConstraint(
                pointer = "id",
                value = "#isPrincipal(ownerId)",
                message = "ownerId should be equals to authorized ID")
})
public class FormDTO implements DTO {

    private String id;

    @NotEmpty
    private String ownerId;

    @NotEmpty(message = "form name is required")
    @Size(max = 32)
    private String name;

    @NotEmpty(message = "form description is required")
    @Size(max = 32)
    private String description;

    @NotNull
    @EnumPattern(regexp = "ACTIVE|INACTIVE|DELETED")
    private FormStatus status;

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FormStatus getStatus() {
        return status;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }
}

