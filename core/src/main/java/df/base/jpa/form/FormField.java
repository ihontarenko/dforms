package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.internal.hibernate.support.ProtectedEntity;
import df.base.jpa.EntityConstants;
import df.base.jpa.EntityGraphConstants;
import df.base.jpa.EntityNameAware;
import df.base.jpa.NamedEntityIdGenerator;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

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
    private Set<Form> forms;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = EntityConstants.TABLE_FORM_FIELD_GROUP,
            inverseJoinColumns = @JoinColumn(name = EntityConstants.COLUMN_FORM_FIELD_GROUP_PARENT_FIELD_ID),
            joinColumns = @JoinColumn(name = EntityConstants.COLUMN_FORM_FIELD_GROUP_FIELD_ID)
    )
    private Set<FormField> child;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private Set<FormFieldOption> options;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private Set<FormFieldAttribute> attributes;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private Set<FormFieldConfig> configs;

    @OneToMany(mappedBy = "formField", fetch = FetchType.LAZY)
    private Set<FormFieldDefaultValue> defaultValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Form> getForms() {
        return forms;
    }

    public void setForms(Set<Form> forms) {
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

    public Set<FormField> getChild() {
        return child;
    }

    public void setChild(Set<FormField> child) {
        this.child = child;
    }

    public void setStatus(FieldStatus status) {
        this.status = status;
    }

    public Set<FormFieldOption> getOptions() {
        return options;
    }

    public void setOptions(Set<FormFieldOption> options) {
        this.options = options;
    }

    public Set<FormFieldAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<FormFieldAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<FormFieldConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Set<FormFieldConfig> configs) {
        this.configs = configs;
    }

    public Set<FormFieldDefaultValue> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(Set<FormFieldDefaultValue> defaultValues) {
        this.defaultValues = defaultValues;
    }

    @Override
    public boolean nonRemovable() {
        return forms != null && forms.size() > 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        FormField field = (FormField) other;

        return new EqualsBuilder()
                .append(id, field.id).append(elementType, field.elementType).append(name, field.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id).append(elementType).append(name)
                .toHashCode();
    }

}

