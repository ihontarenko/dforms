package df.base.jpa.form;

import df.base.common.hibernate.generator.PrefixedId;
import df.base.jpa.DefaultIdGenerator;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "DF_FORM_FIELDS")
public class FormField {

    @Id
    @PrefixedId(
            prefixValue = "FLD",
            sequenceName = "FORM_FIELD",
            prefixGenerator = DefaultIdGenerator.class,
            numberFormat = "%06d",
            initialValue = 100000,
            incrementBy = 1
    )
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

    @OneToMany(mappedBy = "parentField", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FormFieldAssociation> child;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FormFieldOption> options;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FormFieldAttribute> attributes;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FormFieldConfig> configs;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    public List<FormFieldAssociation> getChild() {
        return child;
    }

    public void setChild(List<FormFieldAssociation> child) {
        this.child = child;
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

