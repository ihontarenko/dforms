package df.base.jpa.form;

import df.base.internal.hibernate.generator.PrefixedId;
import df.base.jpa.EntityGraphConstants;
import df.base.jpa.EntityNameAware;
import df.base.jpa.NamedEntityIdGenerator;
import jakarta.persistence.*;

@Entity
@Table(name = "DF_FORM_FIELD_CONFIG")
@NamedEntityGraph(
        name = EntityGraphConstants.FORM_FIELD_CONFIG_WITH_FIELD,
        attributeNodes = @NamedAttributeNode("formField")
)
public class FormFieldConfig implements EntityNameAware {

    @Id
    @PrefixedId(
            prefixValue = "CFG",
            sequenceName = "FORM_FIELD_CONFIG",
            prefixGenerator = NamedEntityIdGenerator.class,
            numberFormat = "%06d",
            incrementBy = 1,
            prefixSeparator = "_"
    )
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", nullable = false)
    private FormField formField;

    @Column(name = "CONFIG_NAME", nullable = false)
    private String configName;

    @Column(name = "CONFIG_VALUE", nullable = false)
    private String configValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormField getFormField() {
        return formField;
    }

    public void setFormField(FormField formField) {
        this.formField = formField;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Override
    public String getName() {
        return getConfigName();
    }
}

