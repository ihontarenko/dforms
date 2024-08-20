package df.base.model.form;

import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.common.jpa.FieldSet;
import df.base.validation.constraint.AuthorizationId;
import df.base.validation.constraint.EnumPattern;
import df.base.validation.constraint.ResourceExistence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static df.base.common.jpa.FieldSet.Comparison.NOT_EQUAL;

@ResourceExistence.Set({
        // validation for creating new form
        @ResourceExistence(
                target = "name",
                fields = {
                        @FieldSet(objectName = "name", entityName = "name")
                },
                entityClass = Form.class,
                message = "[NEW]: form name already taken",
                existence = "id"
        ),
        // validation for updating existing form
        @ResourceExistence(
                target = "name",
                fields = {
                        @FieldSet(objectName = "name", entityName = "name"),
                        @FieldSet(objectName = "id", entityName = "id", comparison = NOT_EQUAL)
                },
                entityClass = Form.class,
                message = "[UPD]: form name already taken",
                existence = "id",
                invert = true
        ),
        // check if request form id is correct
        @ResourceExistence(
                target = "id",
                fields = {
                        @FieldSet(objectName = "id", entityName = "id")
                },
                entityClass = Form.class,
                invert = true,
                unique = false,
                message = "the FORM_ID must exist",
                existence = "id"
        ),
        // check if the request form belongs to the requested user ID
        // skip for ROLE_ADMIN users
        @ResourceExistence(
                target = "ownerId",
                fields = {
                        @FieldSet(objectName = "id", entityName = "id"),
                        @FieldSet(objectName = "ownerId", entityName = "user.id"),
                },
                entityClass = Form.class,
                invert = true,
                unique = false,
                message = "you must own this form to modify it",
                existence = "id",
                authority = "ROLE_ADMIN"
        )
})
public class FormDTO {

    private String id;

    @NotEmpty
    @AuthorizationId
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

