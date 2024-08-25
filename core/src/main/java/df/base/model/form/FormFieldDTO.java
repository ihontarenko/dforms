package df.base.model.form;

import df.base.common.support.JpaCriteria;
import df.base.validation.hibernate.Fields;
import df.base.jpa.form.FormField;
import df.base.validation.hibernate.constraint.JpaResource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static df.base.validation.hibernate.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for creating new field
        @JpaResource(
                target = "name",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = FormField.class,
                message = "[NEW]: field with this name already taken",
                applier = "!#hasText(id)"
        ),
        // validation for updating existing field
        @JpaResource(
                target = "name",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                comparison = JpaCriteria.Comparison.NOT_EQUAL,
                                entityField = "id")
                },
                entityClass = FormField.class,
                message = "[UPD]: field with this name already taken",
                applier = "#hasText(id)"
        )
})
public class FormFieldDTO {

    @Size(max = 32)
    private String id;

    @NotNull
    @Pattern(regexp = "TEXT|NUMBER|SELECT|RADIO|CHECKBOX|TEXTAREA|DATE|EMAIL|URL")
    private String elementType;

    @NotEmpty
    @Size(max = 255)
    private String label;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    @Pattern(regexp = "ACTIVE|INACTIVE|DELETED")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

