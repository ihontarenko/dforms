package df.base.model.form;

import df.base.common.support.JpaCriteria;
import df.base.validation.Fields;
import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.validation.Fields.Value;
import df.base.validation.constraint.EnumPattern;
import df.base.validation.constraint.JpaResource;
import df.base.validation.constraint.SpELConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static df.base.validation.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for creating new form
        @JpaResource(
                target = "name",
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
                target = "name",
                fields = {
                        @Fields(
                                objectValue = @Value(value = "name", type = FIELD_NAME),
                                entityField = "name"
                        ),
                        @Fields(
                                objectValue = @Value(value = "id", type = FIELD_NAME),
                                entityField = "id",
                                comparison = JpaCriteria.Comparison.NOT_EQUAL
                        )
                },
                entityClass = Form.class,
                message = "[UPD]: form name already taken",
                applier = "#hasText(id)" // for existing records
        ),
        // check if request form id is correct
        @JpaResource(
                target = "id",
                fields = {
                        @Fields(
                                objectValue = @Value(value = "id", type = FIELD_NAME),
                                entityField = "id"
                        )
                },
                entityClass = Form.class,
                message = "the FORM_ID must exist",
                applier = "#hasText(id)",
                predicate = "!#result.empty"
        ),
})
@SpELConstraint.List(value = {
        @SpELConstraint(
                target = "id",
                applier = "#hasText(id) && #noRole('SUPER_USER')",
                value = "@formRepository.existsByIdAndUser(id, #getAuthorizedUser())",
                message = "no-no-no... you must be owner or super-user to modify other people's forms"),
        @SpELConstraint(
                target = "id",
                value = "#isPrincipal(ownerId)",
                message = "ownerId should be equals to authorized ID")
})
public class FormDTO {

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

    public String getId() {
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

