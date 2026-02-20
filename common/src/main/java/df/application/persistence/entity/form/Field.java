package df.application.persistence.entity.form;

import df.application.persistence.entity.support.UsageType;
import df.application.persistence.support.EntityConstants;
import df.application.persistence.support.EntityGraphConstants;
import df.common.extensions.hibernate.generator.PrefixedId;
import df.common.persistence.GlobalEntityListener;
import df.application.persistence.entity.EntityNameAware;
import df.application.persistence.generator.NamedEntityIdGenerator;
import df.application.persistence.entity.support.ElementType;
import df.application.persistence.entity.support.FieldStatus;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = EntityConstants.TABLE_FIELDS)
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = EntityGraphConstants.FORM_FIELD_WITH_CONFIGS,
                attributeNodes = @NamedAttributeNode("configs")
        ),
        @NamedEntityGraph(
                name = EntityGraphConstants.FORM_FIELD_FULL,
                attributeNodes = {
                        @NamedAttributeNode("children"),
                        @NamedAttributeNode("parents"),
                        @NamedAttributeNode("configs"),
                        @NamedAttributeNode("attributes"),
                        @NamedAttributeNode("options")
                }
        ),
})
@EntityListeners(GlobalEntityListener.class)
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

    @Enumerated(EnumType.STRING)
    @Column(name = EntityConstants.COLUMN_FIELD_ELEMENT_TYPE, nullable = false)
    private ElementType elementType;

    @Enumerated(EnumType.STRING)
    @Column(name = EntityConstants.COLUMN_FIELD_USAGE_TYPE, nullable = false)
    private UsageType usageType;

    @Column(name = EntityConstants.COLUMN_FIELD_LABEL, nullable = false)
    private String label;

    @Column(name = EntityConstants.COLUMN_FIELD_NAME, nullable = false)
    private String name;

    @Column(name = EntityConstants.COLUMN_FIELD_DESCRIPTION)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = EntityConstants.COLUMN_FIELD_STATUS, nullable = false)
    private FieldStatus status;

    @ManyToMany
    @JoinTable(
            name = EntityConstants.TABLE_FIELD_CHILD_MAPPING,
            joinColumns = @JoinColumn(name = EntityConstants.COLUMN_FIELD_SELF_MAPPING_P_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.COLUMN_FIELD_SELF_MAPPING_C_ID))
    @OrderColumn(name = "SEQUENCE_ORDER")
    private Set<Field> children = new HashSet<>();

    @ManyToMany(mappedBy = EntityConstants.FIELD_FIELD_SELF_MAPPING_CHILD)
    private Set<Field> parents = new HashSet<>();

    @OneToMany(mappedBy = EntityConstants.FIELD_FIELD_OPTIONS_FIELD_ID, fetch = FetchType.LAZY)
    private Set<FieldOption> options;

    @OneToMany(mappedBy = EntityConstants.FIELD_FIELD_ATTRIBUTES_FIELD, fetch = FetchType.LAZY)
    private Set<FieldAttribute> attributes;

    @OneToMany(mappedBy = EntityConstants.FIELD_FIELD_CONFIG_FIELD, fetch = FetchType.LAZY)
    private Set<FieldConfig> configs;

    @OneToMany(mappedBy = EntityConstants.FIELD_FIELD_DEFAULT_VALUES_FIELD, fetch = FetchType.LAZY)
    private Set<FieldDefaultValue> defaultValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Set<Field> getChildren() {
        return children;
    }

    public void setChildren(Set<Field> children) {
        this.children = children;
    }

    public void addChild(Field child) {
        children.add(child);
    }

    public Set<Field> getParents() {
        return parents;
    }

    public void setParents(Set<Field> parents) {
        this.parents = parents;
    }

    public void addParent(Field parent) {
        parents.add(parent);
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

    public void addOption(FieldOption option) {
        options.add(option);
    }

    public Set<FieldAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<FieldAttribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(FieldAttribute attribute) {
        attributes.add(attribute);
    }

    public Set<FieldConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Set<FieldConfig> configs) {
        this.configs = configs;
    }

    public void addConfig(FieldConfig config) {
        configs.add(config);
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

        return field.id.equals(id) && field.elementType == elementType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, elementType);
    }

}

