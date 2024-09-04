package df.base.persistence.entity.form;

import df.base.common.extensions.hibernate.generator.PrefixedId;
import df.base.persistence.support.EntityGraphConstants;
import df.base.persistence.entity.EntityNameAware;
import df.base.persistence.generator.NamedEntityIdGenerator;
import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.support.UsageType;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

import static df.base.persistence.support.EntityConstants.*;

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
        ),
        @NamedEntityGraph(
                name = "FormField.withParentAndChildren",
                attributeNodes = {
                        @NamedAttributeNode("parent"),
                        @NamedAttributeNode(value = "child")
                }
        )
})
public class Field implements EntityNameAware {

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
            name = TABLE_FORM_FIELD_MAPPING,
            joinColumns = @JoinColumn(name = COLUMN_FORM_FIELD_MAPPING_FIELD_ID),
            inverseJoinColumns = @JoinColumn(name = COLUMN_FORM_FIELD_MAPPING_FORM_ID))
    private Set<Form> forms;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_FIELD_ELEMENT_TYPE, nullable = false)
    private ElementType elementType;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_FIELD_USAGE_TYPE, nullable = false)
    private UsageType usageType;

    @Column(name = COLUMN_FIELD_LABEL, nullable = false)
    private String label;

    @Column(name = COLUMN_FIELD_NAME, nullable = false)
    private String name;

    @Column(name = COLUMN_FIELD_DESCRIPTION)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_FIELD_STATUS, nullable = false)
    private FieldStatus status;

    @ManyToMany
    @JoinTable(
            name = TABLE_FIELD_SELF_MAPPING,
            joinColumns = @JoinColumn(name = COLUMN_FIELD_SELF_MAPPING_P_ID),
            inverseJoinColumns = @JoinColumn(name = COLUMN_FIELD_SELF_MAPPING_C_ID))
    @OrderColumn(name = "SEQUENCE_ORDER")
    private Set<Field> child = new HashSet<>();

    @ManyToMany(mappedBy = FIELD_FIELD_SELF_MAPPING_CHILD)
    private Set<Field> parent = new HashSet<>();

    @OneToMany(mappedBy = FIELD_FIELD_OPTIONS_FIELD_ID, fetch = FetchType.LAZY)
    private Set<FieldOption> options;

    @OneToMany(mappedBy = FIELD_FIELD_ATTRIBUTES_FIELD, fetch = FetchType.LAZY)
    private Set<FieldAttribute> attributes;

    @OneToMany(mappedBy = FIELD_FIELD_CONFIG_FIELD, fetch = FetchType.LAZY)
    private Set<FieldConfig> configs;

    @OneToMany(mappedBy = FIELD_FIELD_DEFAULT_VALUES_FIELD, fetch = FetchType.LAZY)
    private Set<FieldDefaultValue> defaultValues;

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

    public UsageType getUsageType() {
        return usageType;
    }

    public void setUsageType(UsageType usageType) {
        this.usageType = usageType;
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

    public Set<Field> getChild() {
        return child;
    }

    public void setChild(Set<Field> child) {
        this.child = child;
    }

    public Set<Field> getParent() {
        return parent;
    }

    public void setParent(Set<Field> parent) {
        this.parent = parent;
    }

    public void setStatus(FieldStatus status) {
        this.status = status;
    }

    public Set<FieldOption> getOptions() {
        return options;
    }

    public void setOptions(Set<FieldOption> options) {
        this.options = options;
    }

    public Set<FieldAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<FieldAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<FieldConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Set<FieldConfig> configs) {
        this.configs = configs;
    }

    public Set<FieldDefaultValue> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(Set<FieldDefaultValue> defaultValues) {
        this.defaultValues = defaultValues;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        Field field = (Field) other;

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

