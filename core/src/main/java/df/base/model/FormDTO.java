package df.base.model;

import df.base.jpa.forms.FormStatus;
import df.base.validation.AuthorizationId;
import df.base.validation.EnumPattern;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FormDTO {

    private String id;

    @NotEmpty
    @AuthorizationId
    private String ownerId;

    @NotEmpty(message = "form name is required")
    @Size(max = 32)
    private String name;

    @NotEmpty
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

