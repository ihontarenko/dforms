package df.application.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import df.application.dto.DTO;

public class FormEntryDTO implements DTO {

    @NotEmpty
    @Size(max = 32)
    private String id;

    @NotEmpty
    @Size(max = 32)
    private String formId;

    @NotNull
    private LocalDateTime createdAt;

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

