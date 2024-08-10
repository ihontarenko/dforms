package df.base.jpa.forms;

import df.base.common.hibernate5.generator.PrefixedId;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "DF_FORM_FIELDS")
public class FormField {

    @Id
    @PrefixedId(prefixValue = "FFD", sequenceName = "FORM_FIELD")
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

    @Enumerated(EnumType.STRING)
    @Column(name = "ELEMENT_TYPE", nullable = false)
    private ElementType elementType;

    @Column(name = "LABEL", nullable = false)
    private String label;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private FieldStatus status;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormFieldOption> options;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormFieldAttribute> attributes;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormFieldConfig> configs;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormFieldDefaultValue> defaultValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
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

    public FieldStatus getStatus() {
        return status;
    }

    public void setStatus(FieldStatus status) {
        this.status = status;
    }

    public List<FormFieldOption> getOptions() {
        return options;
    }

    public void setOptions(List<FormFieldOption> options) {
        this.options = options;
    }

    public List<FormFieldAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<FormFieldAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<FormFieldConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<FormFieldConfig> configs) {
        this.configs = configs;
    }

    public List<FormFieldDefaultValue> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(List<FormFieldDefaultValue> defaultValues) {
        this.defaultValues = defaultValues;
    }
}
