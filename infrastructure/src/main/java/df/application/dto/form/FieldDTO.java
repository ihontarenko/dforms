package df.application.dto.form;

import df.common.support.jpa.JpaCriteria;
import df.common.validation.jakarta.Fields;
import df.application.dto.DTO;
import df.application.persistence.entity.form.Field;
import df.common.validation.jakarta.constraint.JpaResource;
import df.application.validation.groups.Operations;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static df.common.validation.jakarta.Fields.ValueType.FIELD_NAME;

@JpaResource.List({
        // validation for creating new field
        @JpaResource(
                pointer = "name",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name")
                },
                entityClass = Field.class,
                message = "[NEW]: field with this name already taken",
                applier = "!#hasText(id)",
                groups = Operations.Primary.class
        ),
        // validation for updating existing field
        @JpaResource(
                pointer = "name",
                fields = {
                        @Fields(
                                objectValue = @Fields.Value(value = "name", type = FIELD_NAME),
                                entityField = "name"),
                        @Fields(
                                objectValue = @Fields.Value(value = "id", type = FIELD_NAME),
                                comparison = JpaCriteria.Comparison.NOT_EQUAL,
                                entityField = "id")
                },
                entityClass = Field.class,
                message = "[UPD]: field with this name already taken",
                applier = "#hasText(id)",
                groups = Operations.Primary.class
        )
})
public class FieldDTO implements DTO {

    @NotNull(groups = Operations.PrimaryId.class)
    @Size(max = 32, groups = Operations.Primary.class)
    private String id;

    @NotNull(groups = Operations.Primary.class)
    @Pattern(regexp = "VIRTUAL|EMBEDDABLE|STANDALONE", groups = Operations.Primary.class)
    private String usageType;

    @NotNull(groups = Operations.Primary.class)
    @Pattern(regexp = "TEXT|NUMBER|SELECT|RADIO|CHECKBOX|TEXTAREA|DATE|EMAIL|URL|NONE",
            groups = Operations.Primary.class)
    private String elementType;

    @NotEmpty(groups = {Operations.Primary.class})
    @Size(max = 255)
    private String label;

    @NotEmpty(groups = {Operations.Primary.class})
    @Size(max = 255, groups = {Operations.Primary.class})
    private String name;

    @Size(max = 500, groups = {Operations.Primary.class})
    private String description;

    @NotNull(groups = Operations.Primary.class)
    @Pattern(regexp = "ACTIVE|INACTIVE|DELETED", groups = Operations.Primary.class)
    private String status;

    private Map<String, FieldDTO> parents = new HashMap<>();

    private Map<String, FieldDTO> children = new HashMap<>();

    @Valid
    private List<FieldOptionDTO> options = new ArrayList<>();

    @Valid
    private List<FieldAttributeDTO> attributes = new ArrayList<>();

    @Valid
    private List<FieldConfigDTO> configs = new ArrayList<>();

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
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

    public Map<String, FieldDTO> getParents() {
        return parents;
    }

    public void setParents(Map<String, FieldDTO> parents) {
        this.parents = parents;
    }

    public void addParent(FieldDTO parent) {
        parents.put(parent.id(), parent);
    }

    public Map<String, FieldDTO> getChildren() {
        return children;
    }

    public void setChildren(Map<String, FieldDTO> children) {
        this.children = children;
    }

    public void addChild(FieldDTO child) {
        children.put(child.id(), child);
    }

    public List<FieldOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<FieldOptionDTO> options) {
        this.options = options;
    }

    public void addOption(FieldOptionDTO option) {
        options.add(option);
    }

    public List<FieldAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<FieldAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(FieldAttributeDTO attribute) {
        attributes.add(attribute);
    }

    public List<FieldConfigDTO> getConfigs() {
        return configs;
    }

    public void setConfigs(List<FieldConfigDTO> configs) {
        this.configs = configs;
    }

    public void addConfig(FieldConfigDTO config) {
        configs.add(config);
    }
}

