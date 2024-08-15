package df.base.model.form;

import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.validation.AuthorizationId;
import df.base.validation.EnumPattern;
import df.base.validation.Unique;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Unique.Set({
        @Unique(
                target = "name",
                fields = {
                        @Unique.Field(
                                objectName = "name",
                                entityName = "name"
                        )
                },
                entityClass = Form.class,
                message = "form name already taken",
                existence = "id"
        ),
        @Unique(
                target = "id",
                fields = {
                        @Unique.Field(
                                objectName = "id",
                                entityName = "id"
                        ),
                },
                entityClass = Form.class,
                reverse = true,
                unique = false,
                message = "the FORM_ID must exist",
                existence = "id"
        ),
        @Unique(
                target = "ownerId",
                fields = {
                        @Unique.Field(
                                objectName = "id",
                                entityName = "id"
                        ),
                        @Unique.Field(
                                objectName = "ownerId",
                                entityName = "user.id"
                        ),
                },
                entityClass = Form.class,
                reverse = true,
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

