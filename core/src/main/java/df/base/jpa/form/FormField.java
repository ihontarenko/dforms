package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.internal.hibernate.support.ProtectedEntity;
import df.base.jpa.EntityGraphConstants;
import df.base.jpa.EntityNameAware;
import df.base.jpa.NamedEntityIdGenerator;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "DF_FORM_FIELDS")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = EntityGraphConstants.FORM_FIELD_WITH_CONFIGS,
                attributeNodes = @NamedAttributeNode("configs")
        ),
        @NamedEntityGraph(
                name = EntityGraphConstants.FORM_FIELD_WITH_ALL_RELATED,
                attributeNodes = {
                        @NamedAttributeNode("configs"),
                        @NamedAttributeNode("attributes"),
                        @NamedAttributeNode("options")
                }
        )
})
public class FormField implements EntityNameAware, ProtectedEntity {

    @Id
    @PrefixedId(
            prefixValue = "F",
            sequenceName = "FORM_FIELD",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%04d",
            initialValue = 1000,
            incrementBy = 1,
            prefixSeparator = "_"
    )
    @Column(name = "ID")
    private String id;

    @ManyToMany
    @JoinTable(
            name = "DF_FORM_FIELD_MAPPING",
            joinColumns = @JoinColumn(name = "FIELD_ID"),
            inverseJoinColumns = @JoinColumn(name = "FORM_ID")
    )
    private List<Form> forms;

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

    @OneToMany(mappedBy = "parentField", fetch = FetchType.LAZY)
    private List<FormFieldGroup> child;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private List<FormFieldOption> options;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private List<FormFieldAttribute> attributes;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private List<FormFieldConfig> configs;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private List<FormFieldDefaultValue> defaultValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
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

    public List<FormFieldGroup> getChild() {
        return child;
    }

    public void setChild(List<FormFieldGroup> child) {
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

    @Override
    public boolean nonRemovable() {
        return forms != null && forms.size() > 0;
    }

}

